package com.usanov.controller;

import com.usanov.models.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {

    public static void showWaitingController(Stage primaryStage, int PORT) throws IOException {

        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("/view/waiting.fxml"));
        Parent root = loader.load();

        WaitingController waitingController = (WaitingController) loader.getController();
        waitingController.init(primaryStage, PORT);

        primaryStage.setTitle("Bouncers");
        primaryStage.getScene().setRoot(root);
        primaryStage.show();
    }

    public static void showGameController(Stage primaryStage, Player player) throws IOException {

        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("/view/game.fxml"));
        Parent root = loader.load();

        GameController gameController = (GameController) loader.getController();
        gameController.init(primaryStage, player);

        primaryStage.setTitle("Bouncers");
        primaryStage.getScene().setRoot(root);
        primaryStage.show();
    }

    public static void showStartController(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("/view/start.fxml"));
        Parent root = loader.load();

        StartController startController = (StartController) loader.getController();
        startController.init(new Player(), primaryStage);

        primaryStage.setTitle("Bouncers");
        primaryStage.getScene().setRoot(root);
        primaryStage.show();
    }
}