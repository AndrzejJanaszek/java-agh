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
        });
    }

    @FXML
    public void loginAsUser() {
        SceneManager.switchScene("user-view.fxml", controller -> {
        });
    }
}
