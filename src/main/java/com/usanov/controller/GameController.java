package com.usanov.controller;

import com.usanov.models.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {

    Player player;
    Stage primaryStage;
    Thread serverListener = new Thread(new ServerListener());

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Rectangle playerRectangle;
    @FXML
    private Rectangle opponentRectangle;
    @FXML
    private Circle ballFirst;
    @FXML
    private Circle ballSecond;
    @FXML
    private Text result;
    @FXML
    private Text clickForMenuLabel;

    private void setOpponentPositionY(double opponentPositionY) {
        opponentRectangle.setLayoutY(opponentPositionY);
    }
    private void setXBallFirst(double positionX) {
        ballFirst.setCenterX(positionX);
    }
    private void setXBallSecond(double positionX) {
        ballSecond.setCenterX(positionX);
    }
    private void setYBallFirst(double positionY) {
        ballFirst.setCenterY(positionY);
    }
    private void setYBallSecond(double positionY) {
        ballSecond.setCenterY(positionY);
    }

    private void setResult(String result) {

        this.result.setText(result);
        clickForMenuLabel.setText("Click for menu");
        anchorPane.setOnMouseMoved(null);

        anchorPane.setOnMouseClicked(e -> {
            try {
                SceneLoader.showStartController(primaryStage);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        serverListener.interrupt();
        player = null;
    }

    public void init(Stage primaryStage, Player player) {

        this.primaryStage = primaryStage;
        this.player = player;

        anchorPane.setOnMouseMoved(e -> {
            playerRectangle.setLayoutY(e.getY());
            sendPositionToSever(e.getY());
        });

        player.getPrintWriter().println(250);
        serverListener.start();
    }

    private void sendPositionToSever(double y) {
        player.getPrintWriter().println(y);
    }

    private class ServerListener implements Runnable {

        @Override
        public void run() {
            try {
                while (player.getScanner().hasNext()) {

                    String answer = player.getScanner().nextLine();

                    if (answer.startsWith("bxF")) {
                        String ballX = answer.split(":")[1];
                        Platform.runLater(() -> setXBallFirst(Double.parseDouble(ballX)));

                    } else if (answer.startsWith("byF")) {
                        String ballY = answer.split(":")[1];
                        Platform.runLater(() -> setYBallFirst(Double.parseDouble(ballY)));

                    } else if (answer.startsWith("bxS")) {
                        String ballX = answer.split(":")[1];
                        Platform.runLater(() -> setXBallSecond(Double.parseDouble(ballX)));

                    } else if (answer.startsWith("byS")) {
                        String ballY = answer.split(":")[1];
                        Platform.runLater(() -> setYBallSecond(Double.parseDouble(ballY)));

                    } else if (answer.startsWith("oy")) {
                        String opponentY = answer.split(":")[1];
                        Platform.runLater(() -> setOpponentPositionY(Double.parseDouble(opponentY)));

                    } else if (answer.startsWith("go")) {
                        String result = answer.split(":")[1];
                        Platform.runLater(() -> setResult(result));
                    }
                }
            } catch(NullPointerException e){
                System.out.println("null");
            }
        }
    }
}