package com.example.koniary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage mainStage;

    public static void initialize(Stage stage) {
        mainStage = stage;
    }

    public static void switchScene(String fxmlName, java.util.function.Consumer<Object> controllerInit) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlName));
            Scene scene = new Scene(loader.load());

            Object controller = loader.getController();
            controllerInit.accept(controller);

            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchScene(String fxmlName) {
        switchScene(fxmlName, c -> {});
    }
}
