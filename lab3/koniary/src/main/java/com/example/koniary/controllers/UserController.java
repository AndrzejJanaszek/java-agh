package com.example.koniary.controllers;

import com.example.koniary.HibernateUtil;
import com.example.koniary.model.*;
import com.example.koniary.services.HorseDAO;
import com.example.koniary.services.RatingDAO;
import com.example.koniary.services.StableDAO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.Comparator;
import java.util.List;

public class UserController {

    private StableDAO stableDAO = new StableDAO(HibernateUtil.getEntityManagerFactory());
    private HorseDAO horseDAO = new HorseDAO(HibernateUtil.getEntityManagerFactory());
    private RatingDAO ratingDAO = new RatingDAO(HibernateUtil.getEntityManagerFactory());

    @FXML private TableView<Stable> stableTable;
    @FXML private TableColumn<Stable, String> colStableName;
    @FXML private TableColumn<Stable, Integer> colStableCapacity;
    @FXML private TableColumn<Stable, Integer> colStableLoad;

    @FXML private TableView<Horse> horseTable;
    @FXML private TableColumn<Horse, String> colHorseName;
    @FXML private TableColumn<Horse, String> colHorseBreed;
    @FXML private TableColumn<Horse, Integer> colHorseAge;
    @FXML private TableColumn<Horse, Double> colHorseWeight;
    @FXML private TableColumn<Horse, Double> colHorsePrice;
    @FXML private TableColumn<Horse, HorseCondition> colHorseStatus;

    // NOWE
    @FXML private TableColumn<Horse, Double> colRatingAvg;
    @FXML private TableColumn<Horse, Integer> colRatingCount;

    @FXML private TextField filterField;
    @FXML private ComboBox<HorseCondition> conditionCombo;

    private final ObservableList<Stable> stables = FXCollections.observableArrayList();
    private final ObservableList<Horse> horses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupStableTable();
        setupHorseTable();
        setupListeners();
        loadData();
    }

    private void loadData() {
        stables.setAll(stableDAO.getAll());
    }

    private void setupStableTable() {
        colStableName.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getStableName()));
        colStableCapacity.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getMaxCapacity()).asObject());

        colStableLoad.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getHorseList().size()).asObject());

        stableTable.setItems(stables);
    }

    private void setupHorseTable() {

        colHorseName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colHorseBreed.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBreed()));
        colHorseAge.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getAge()).asObject());
        colHorseWeight.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getWeight()).asObject());
        colHorsePrice.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrice()).asObject());
        colHorseStatus.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getStatus()));

        // NOWE — dynamiczne pobieranie ocen
        colRatingAvg.setCellValueFactory(c ->
                new SimpleDoubleProperty(
                        ratingDAO.getAverageForHorse(c.getValue().getId())
                ).asObject()
        );

        colRatingCount.setCellValueFactory(c ->
                new SimpleIntegerProperty(
                        ratingDAO.getRatingCountForHorse(c.getValue().getId()).intValue()
                ).asObject()
        );

        horseTable.setItems(horses);
        conditionCombo.setItems(FXCollections.observableArrayList(HorseCondition.values()));
    }

    private void setupListeners() {
        stableTable.getSelectionModel().selectedItemProperty().addListener((obs, old, stable) -> {
            if (stable != null) {
                horses.setAll(stable.getHorseList());
            }
        });
    }

    /* ======================
       SORTING
       ====================== */

    @FXML
    public void sortByPrice() {
        List<Horse> sorted = horses.stream()
                .sorted(Comparator.comparingDouble(Horse::getPrice))
                .toList();
        horses.setAll(sorted);
    }

    @FXML
    public void sortByAge() {
        List<Horse> sorted = horses.stream()
                .sorted(Comparator.comparingInt(Horse::getAge))
                .toList();
        horses.setAll(sorted);
    }

    /* ======================
       FILTERING
       ====================== */

    @FXML
    public void filterHorses() {
        Stable selected = stableTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        String text = filterField.getText().toLowerCase();
        HorseCondition cond = conditionCombo.getValue();

        List<Horse> filtered = selected.getHorseList().stream()
                .filter(h -> h.getName().toLowerCase().contains(text))
                .filter(h -> cond == null || h.getStatus() == cond)
                .toList();

        horses.setAll(filtered);
    }

    /* ======================
       ADD RATING
       ====================== */

    @FXML
    public void addRating() {
        Horse horse = horseTable.getSelectionModel().getSelectedItem();

        if (horse == null) {
            showError("Select a horse first.");
            return;
        }

        Dialog<Rating> dialog = new Dialog<>();
        dialog.setTitle("Add Rating");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField valueField = new TextField();
        valueField.setPromptText("0–5");

        TextField descField = new TextField();
        descField.setPromptText("Description");

        VBox vbox = new VBox(10, new Label("Rating:"), valueField,
                new Label("Description:"), descField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    int val = Integer.parseInt(valueField.getText());
                    if (val < 0 || val > 5) throw new Exception();

                    Rating r = new Rating();
                    r.setHorse(horse);
                    r.setValue(val);
                    r.setDescription(descField.getText());
                    r.setDate(java.time.LocalDate.now());
                    return r;

                } catch (Exception e) {
                    showError("Invalid rating value.");
                    return null;
                }
            }
            return null;
        });

        Rating rating = dialog.showAndWait().orElse(null);
        if (rating == null) return;

        ratingDAO.save(rating);

        horseTable.refresh();
    }

    /* ======================
       SHOW RATINGS
       ====================== */

    @FXML
    public void showRatings() {
        Horse horse = horseTable.getSelectionModel().getSelectedItem();

        if (horse == null) {
            showError("Select a horse first.");
            return;
        }

        List<Rating> list = ratingDAO.getRatingsForHorse(horse.getId());

        StringBuilder sb = new StringBuilder();
        for (Rating r : list) {
            sb.append("⭐ ").append(r.getValue()).append("  •  ")
                    .append(r.getDate()).append("\n")
                    .append(r.getDescription()).append("\n\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ratings for " + horse.getName());
        alert.setHeaderText(null);
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    @FXML
    public void contactAdmin() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contact");
        alert.setHeaderText("Contact the administrator");
        alert.setContentText("Please email: admin@stable-system.com");
        alert.showAndWait();
    }

}
