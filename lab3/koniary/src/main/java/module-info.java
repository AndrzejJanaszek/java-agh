module com.example.koniary {
    requires javafx.controls;
    requires javafx.fxml;

    // Pakiety otwarte dla refleksji (FXML loader)
    opens com.example.koniary to javafx.fxml;
    opens com.example.koniary.controllers to javafx.fxml;
    opens com.example.koniary.model to javafx.fxml;

    // Pakiety eksportowane (widoczne dla innych modułów)
    exports com.example.koniary;
    exports com.example.koniary.controllers;
    exports com.example.koniary.model;
}