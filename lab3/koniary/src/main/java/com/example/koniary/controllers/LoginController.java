package com.example.koniary.controllers;

import com.example.koniary.SceneManager;
import com.example.koniary.model.StableManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    private final StableManager stableManager = new StableManager();

    @FXML
    public void loginAsAdmin() {
        SceneManager.switchScene("admin-view.fxml", controller -> {
            if (controller instanceof AdminController admin) {
                admin.setStableManager(stableManager);
            }
        });
    }

    @FXML
    public void loginAsUser() {
        System.out.println("Logowanie jako użytkownik...");
        SceneManager.switchScene("user-view.fxml");
    }
}
