package com.example.koniary.controllers;

import com.example.koniary.model.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Comparator;
import java.util.List;

public class UserController {

    private StableManager stableManager;

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
    }

    public void setStableManager(StableManager manager) {
        this.stableManager = manager;
        stables.setAll(manager.getStables().values());
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
}
