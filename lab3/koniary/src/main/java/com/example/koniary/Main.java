package com.example.koniary;

import com.example.koniary.model.StableManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final StableManager stableManager = new StableManager();

    public static StableManager getStableManager() {
        return stableManager;
    }

    @Override
    public void start(Stage stage) throws Exception {

        stableManager.loadDefaultData(); // domyślne dane

        SceneManager.init(stage);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

