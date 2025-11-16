package com.example.koniary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class SceneManager {

    public static Stage mainStage;

    public static void init(Stage stage) {
        mainStage = stage;
    }

    public static void switchScene(String fxml, Consumer<Object> controllerSetup) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxml));
            Scene scene = new Scene(loader.load());

            Object controller = loader.getController();
            if (controllerSetup != null) {
                controllerSetup.accept(controller);
            }

            mainStage.setScene(scene);  // <<< tutaj wcześniej było null
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchScene(String fxml) {
        switchScene(fxml, null);
    }
}
