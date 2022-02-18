package com.usanov.game;

import com.usanov.controller.SceneLoader;
import com.usanov.controller.StartController;
import com.usanov.models.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Player player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        player = new Player();
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("/view/start.fxml"));
        Parent root = loader.load();

        stage.setTitle("Game");
        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);

        StartController startController = (StartController) loader.getController();
        startController.init(player, stage);

        stage.show();
    }
}
