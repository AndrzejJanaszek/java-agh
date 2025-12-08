package com.example.koniary.controllers;

import com.example.koniary.HibernateUtil;
import com.example.koniary.exceptions.*;
import com.example.koniary.model.*;
import com.example.koniary.services.HorseDAO;
import com.example.koniary.services.Serializer;
import com.example.koniary.services.StableDAO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.util.Comparator;
import java.util.List;

public class AdminController {

    // === DAO ZAMIAST StableManager ===
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

    // FILTER CONTROLS
    @FXML private TextField filterField;
    @FXML private ComboBox<HorseCondition> conditionCombo;

    // DATA
    private final ObservableList<Horse> horses = FXCollections.observableArrayList();
    private final ObservableList<Stable> stables = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupStableTable();
        setupHorseTable();
        setupListeners();
        loadDummyData(); // test data into DB

        stableTable.setEditable(true);
        horseTable.setEditable(true);
    }

    private void setupStableTable() {

        colStableName.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getStableName()));
        colStableCapacity.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getMaxCapacity()).asObject());
        colStableLoad.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getHorseList().size()).asObject());

        // ==== EDITABLE ====
        colStableName.setCellFactory(TextFieldTableCell.forTableColumn());
        colStableName.setOnEditCommit(event -> {
            Stable stable = event.getRowValue();
            stable.setStableName(event.getNewValue());
            stableDAO.update(stable);
            stables.setAll(stableDAO.getAll());
            stableTable.refresh();
        });

        colStableCapacity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colStableCapacity.setOnEditCommit(event -> {
            Stable stable = event.getRowValue();
            stable.setMaxCapacity(event.getNewValue());
            stableDAO.update(stable);
            stableTable.refresh();
        });

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

        // ==== EDITABLE ====

        colHorseName.setCellFactory(TextFieldTableCell.forTableColumn());
        colHorseName.setOnEditCommit(event -> {
            Horse h = event.getRowValue();
            h.setName(event.getNewValue());
            horseDAO.update(h);
            horseTable.refresh();
        });

        colHorseBreed.setCellFactory(TextFieldTableCell.forTableColumn());
        colHorseBreed.setOnEditCommit(event -> {
            Horse h = event.getRowValue();
            h.setBreed(event.getNewValue());
            horseDAO.update(h);
            horseTable.refresh();
        });

        colHorseAge.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        colHorseAge.setOnEditCommit(event -> {
            Horse h = event.getRowValue();
            h.setAge(event.getNewValue());
            horseDAO.update(h);
            horseTable.refresh();
        });

        colHorseWeight.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        colHorseWeight.setOnEditCommit(event -> {
            Horse h = event.getRowValue();
            h.setWeight(event.getNewValue());
            horseDAO.update(h);
            horseTable.refresh();
        });

        colHorsePrice.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        colHorsePrice.setOnEditCommit(event -> {
            Horse h = event.getRowValue();
            h.setPrice(event.getNewValue());
            horseDAO.update(h);
            horseTable.refresh();
        });

        // === STATUS DROPDOWN ===
        colHorseStatus.setCellFactory(col -> new TableCell<>() {
            private final ComboBox<HorseCondition> combo =
                    new ComboBox<>(FXCollections.observableArrayList(HorseCondition.values()));

            {
                combo.valueProperty().addListener((obs, oldVal, newVal) -> {
                    if (getTableRow().getItem() != null) {
                        Horse h = getTableRow().getItem();
                        h.setStatus(newVal);
                        horseDAO.update(h);
                    }
                });
            }

            @Override
            protected void updateItem(HorseCondition status, boolean empty) {
                super.updateItem(status, empty);
                if (empty) setGraphic(null);
                else {
                    combo.setValue(status);
                    setGraphic(combo);
                }
            }
        });

        horseTable.setItems(horses);
    }

    private void setupListeners() {
        stableTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                horses.setAll(selected.getHorseList());
            }
        });
    }

    // LOAD TEST DATA INTO DB ON START
    private void loadDummyData() {
        if (!stableDAO.getAll().isEmpty()) {
            stables.setAll(stableDAO.getAll());
            return; // don't recreate data if DB already has some
        }

        try {
            Stable s1 = new Stable("Stadnina Jednorożców", 8);
            s1.addHorse(new Horse("Błyskotek", "Jednorożec Górski", HorseType.HOT_BLOODED,
                    HorseCondition.HEALTHY, 120, 20000, 380));
            s1.addHorse(new Horse("Pastelozaur", "Jednorożec Pastelowy", HorseType.HOT_BLOODED,
                    HorseCondition.TRAINING, 45, 18000, 350));

            Stable s2 = new Stable("Wiedźmińska Zagroda", 12);
            s2.addHorse(new Horse("Płotka", "Koń Geralta", HorseType.COLD_BLOODED,
                    HorseCondition.HEALTHY, 7, 5000, 420));
            s2.addHorse(new Horse("Kasztanek", "Koń Temerski", HorseType.COLD_BLOODED,
                    HorseCondition.SICK, 10, 2400, 480));
            s2.addHorse(new Horse("Bucefał", "Koń Nilfgaardzki", HorseType.HOT_BLOODED,
                    HorseCondition.TRAINING, 6, 5200, 460));

            Stable s3 = new Stable("Zagroda Podlaska", 6);
            s3.addHorse(new Horse("Zenek", "Koń Disco Polo", HorseType.COLD_BLOODED,
                    HorseCondition.HEALTHY, 4, 3000, 500));
            s3.addHorse(new Horse("Klarynek", "Koń Podlaski", HorseType.COLD_BLOODED,
                    HorseCondition.QUARANTINE, 5, 2500, 550));

            Stable s4 = new Stable("Stajnia Konia Płotki", 4);
            s4.addHorse(new Horse("Płotka 2.0", "Koń Mutant", HorseType.HOT_BLOODED,
                    HorseCondition.TRAINING, 3, 7000, 390));
            s4.addHorse(new Horse("Bugowóz", "Koń, który przenika przez ściany", HorseType.HOT_BLOODED,
                    HorseCondition.HEALTHY, 2, 9999, 300));

            Stable s5 = new Stable("Koński Raj Mordoru", 10);
            s5.addHorse(new Horse("Ognik", "Koń Ognisty", HorseType.HOT_BLOODED,
                    HorseCondition.HEALTHY, 12, 8000, 610));
            s5.addHorse(new Horse("Czarny Jeździec", "Rumak Nazgula", HorseType.HOT_BLOODED,
                    HorseCondition.SICK, 100, 15000, 700));

            stableDAO.save(s1);
            stableDAO.save(s2);
            stableDAO.save(s3);
            stableDAO.save(s4);
            stableDAO.save(s5);

            stables.setAll(stableDAO.getAll());

        } catch (Exception e) {
            System.err.println("Błąd przy ładowaniu danych testowych: " + e.getMessage());
        }
    }

    /* ============================================
     * ADD STABLE
     * ============================================ */
    @FXML
    public void addStable() {

        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setHeaderText("Add new stable");
        nameDialog.setContentText("Stable name:");
        String name = nameDialog.showAndWait().orElse(null);
        if (name == null || name.isBlank()) return;

        TextInputDialog capDialog = new TextInputDialog();
        capDialog.setHeaderText("Add new stable");
        capDialog.setContentText("Max capacity:");
        String capStr = capDialog.showAndWait().orElse(null);

        int cap;
        try {
            cap = Integer.parseInt(capStr);
        } catch (Exception e) {
            showError("Capacity must be a number.");
            return;
        }

        Stable stable = new Stable(name, cap);
        stableDAO.save(stable);

        stables.setAll(stableDAO.getAll());
    }

    /* ============================================
     * REMOVE STABLE
     * ============================================ */
    @FXML
    public void removeStable() {

        Stable selected = stableTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("No stable selected.");
            return;
        }

        stableDAO.delete(selected);

        stables.setAll(stableDAO.getAll());
        horses.clear();
    }

    /* ============================================
     * SORT STABLES BY LOAD
     * ============================================ */
    @FXML
    public void sortStables() {
        List<Stable> sorted = stableDAO.getAll().stream()
                .sorted(Comparator.comparingInt(s -> s.getHorseList().size()))
                .toList();

        stables.setAll(sorted);
    }

    /* ============================================
     * ADD HORSE
     * ============================================ */
    @FXML
    public void addHorse() {

        Stable selectedStable = stableTable.getSelectionModel().getSelectedItem();

        if (selectedStable == null) {
            showError("Select a stable first.");
            return;
        }

        Dialog<Horse> dialog = new Dialog<>();
        dialog.setTitle("Add horse");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField breedField = new TextField();
        breedField.setPromptText("Breed");

        ComboBox<HorseType> typeCombo = new ComboBox<>(FXCollections.observableArrayList(HorseType.values()));
        ComboBox<HorseCondition> condCombo = new ComboBox<>(FXCollections.observableArrayList(HorseCondition.values()));

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        TextField weightField = new TextField();
        weightField.setPromptText("Weight");

        grid.addRow(0, new Label("Name:"), nameField);
        grid.addRow(1, new Label("Breed:"), breedField);
        grid.addRow(2, new Label("Type:"), typeCombo);
        grid.addRow(3, new Label("Condition:"), condCombo);
        grid.addRow(4, new Label("Age:"), ageField);
        grid.addRow(5, new Label("Price:"), priceField);
        grid.addRow(6, new Label("Weight:"), weightField);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    return new Horse(
                            nameField.getText(),
                            breedField.getText(),
                            typeCombo.getValue(),
                            condCombo.getValue(),
                            Integer.parseInt(ageField.getText()),
                            Double.parseDouble(priceField.getText()),
                            Double.parseDouble(weightField.getText())
                    );
                } catch (Exception e) {
                    showError("Invalid input.");
                    return null;
                }
            }
            return null;
        });

        Horse horse = dialog.showAndWait().orElse(null);
        if (horse == null) return;

        try {
            selectedStable.addHorse(horse);
        } catch (StableFullException e) {
            showError("Stajnia jest pełna: " + e.getMessage());
        }

        stableDAO.update(selectedStable);

        horses.setAll(selectedStable.getHorseList());
        stableTable.refresh();
    }

    /* ============================================
     * REMOVE HORSE
     * ============================================ */
    @FXML
    public void removeHorse() {

        Stable selectedStable = stableTable.getSelectionModel().getSelectedItem();
        Horse selectedHorse = horseTable.getSelectionModel().getSelectedItem();

        if (selectedStable == null) {
            showError("Select a stable first.");
            return;
        }
        if (selectedHorse == null) {
            showError("Select a horse.");
            return;
        }

        selectedStable.removeHorse(selectedHorse);
        stableDAO.update(selectedStable);

        horses.setAll(selectedStable.getHorseList());
        stableTable.refresh();
    }

    /* ============================================
     * FILTER HORSES
     * ============================================ */
    @FXML
    public void filterHorses() {

        Stable selectedStable = stableTable.getSelectionModel().getSelectedItem();
        if (selectedStable == null) {
            return;
        }

        String nameFilter = filterField.getText().toLowerCase();
        HorseCondition conditionFilter = conditionCombo.getValue();

        List<Horse> fullList = selectedStable.getHorseList();

        List<Horse> filtered = fullList.stream()
                .filter(h -> h.getName().toLowerCase().contains(nameFilter))
                .filter(h -> conditionFilter == null || h.getStatus() == conditionFilter)
                .toList();

        horses.setAll(filtered);
    }

    /* ============================================
     * SHOW ERROR POPUP
     * ============================================ */
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void saveData() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save data");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary file", "*.dat"));

        File file = fc.showSaveDialog(null);
        if (file == null) return;

        Serializer.saveToFile(stableDAO.getAll(), file);
    }

    private void refreshTables() {
        stables.setAll(stableDAO.getAll());   // odśwież listę stajni

        Stable selected = stableTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            horses.setAll(selected.getHorseList());  // odśwież konie wybranej stajni
        }

        stableTable.refresh();
        horseTable.refresh();
    }


    @FXML
    public void loadData() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load data");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary file", "*.dat"));

        File file = fc.showOpenDialog(null);
        if (file == null) return;

        List<Stable> loaded = Serializer.loadFromFile(file);
        if (loaded != null) {
            // UWAGA: to już zwykłe POJO, a nie encje!
            // Możemy je teraz zapisać do bazy:
            for (Stable s : loaded) {
                stableDAO.save(s);
            }
            refreshTables();
        }
    }

}
