package com.usanov.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WaitingController {

    private Stage primaryStage;

    @FXML
    private Text portLabel;

    public void init(Stage primaryStage, int PORT) {

        this.primaryStage = primaryStage;
        int port = PORT;

        portLabel.setText(String.valueOf(port));
    }
}