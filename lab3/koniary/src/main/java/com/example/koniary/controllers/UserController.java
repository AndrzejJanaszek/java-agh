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
import javafx.scene.layout.GridPane;

import java.util.Comparator;
import java.util.List;

public class UserController {

    // === DAO zamiast StableManager ===
    private final StableDAO stableDAO = new StableDAO(HibernateUtil.getEntityManagerFactory());
    private final HorseDAO horseDAO = new HorseDAO(HibernateUtil.getEntityManagerFactory());

    // TABLES
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

    @FXML private TextField filterField;
    @FXML private ComboBox<HorseCondition> conditionCombo;

    private final ObservableList<Stable> stables = FXCollections.observableArrayList();
    private final ObservableList<Horse> horses = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        setupStableTable();
        setupHorseTable();
        setupListeners();

        // Wczytanie danych z bazy
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

        colHorseName.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getName()));
        colHorseBreed.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getBreed()));
        colHorseAge.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getAge()).asObject());
        colHorseWeight.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getWeight()).asObject());
        colHorsePrice.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getPrice()).asObject());
        colHorseStatus.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getStatus()));

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
       CONTACT ADMIN
       ====================== */

    @FXML
    public void contactAdmin() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contact");
        alert.setHeaderText("Contact the administrator");
        alert.setContentText("Please email: admin@stable-system.com");
        alert.showAndWait();
    }

    // =============================================
    // ADD RATING
    // =============================================

    // Dodaj to pole w klasie UserController:
    private final RatingDAO ratingDAO = new RatingDAO();

    @FXML
    public void addRating() {

        Horse selectedHorse = horseTable.getSelectionModel().getSelectedItem();
        if (selectedHorse == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No horse selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a horse to rate.");
            alert.showAndWait();
            return;
        }

        Dialog<Rating> dialog = new Dialog<>();
        dialog.setTitle("Add Rating");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(10));

        TextField starsField = new TextField();
        starsField.setPromptText("Stars (1–5)");

        TextArea commentField = new TextArea();
        commentField.setPromptText("Comment");
        commentField.setPrefRowCount(3);

        grid.addRow(0, new Label("Stars:"), starsField);
        grid.addRow(1, new Label("Comment:"), commentField);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    int stars = Integer.parseInt(starsField.getText());

                    Rating rating = new Rating(stars, commentField.getText(), selectedHorse);
                    return rating;

                } catch (NumberFormatException e) {
                    showError("Stars must be a number between 1 and 5.");
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                }
            }
            return null;
        });

        Rating rating = dialog.showAndWait().orElse(null);
        if (rating == null) return;

        ratingDAO.save(rating);

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Rating added");
        success.setHeaderText(null);
        success.setContentText("Rating saved successfully!");
        success.showAndWait();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void showRatings() {

        Horse horse = horseTable.getSelectionModel().getSelectedItem();
        if (horse == null) {
            showError("Select a horse first.");
            return;
        }

        // Pobieramy oceny z DB
        List<Rating> ratings = ratingDAO.findByHorseId(horse.getId());

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Ratings for: " + horse.getName());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        TableView<Rating> table = new TableView<>();
        table.setPrefWidth(450);

        TableColumn<Rating, Integer> colStars = new TableColumn<>("Stars");
        colStars.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getStars()).asObject());
        colStars.setPrefWidth(60);

        TableColumn<Rating, String> colComment = new TableColumn<>("Comment");
        colComment.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getComment()));
        colComment.setPrefWidth(250);

        TableColumn<Rating, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCreatedAt().toString())
        );
        colDate.setPrefWidth(140);

        table.getColumns().addAll(colStars, colComment, colDate);
        table.getItems().addAll(ratings);

        dialog.getDialogPane().setContent(table);
        dialog.showAndWait();
    }


}
