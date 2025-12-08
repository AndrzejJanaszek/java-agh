package com.example.koniary.controllers;

import com.example.koniary.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void loginAsAdmin() {
        SceneManager.switchScene("admin-view.fxml", controller -> {
            // Brak potrzeby przekazywania czegokolwiek
            // AdminController sam korzysta z DAO
        });
    }

    @FXML
    public void loginAsUser() {
        SceneManager.switchScene("user-view.fxml", controller -> {
            // Brak potrzeby przekazywania czegokolwiek
            // UserController sam korzysta z DAO
        });
    }
}
