package com.example.koniary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.init(stage);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        var emf = HibernateUtil.getEntityManagerFactory();
        System.out.println("Hibernate działa! EMF = " + emf);

        launch();
    }
}
