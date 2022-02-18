package com.usanov.server;

import com.usanov.models.Ball;
import com.usanov.models.Model;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainThread implements Runnable {

    private static final int width = 1000;
    private static final int height = 1000;

    private Thread firstPlayerListener = new Thread(new FirstPlayerListener());
    private Thread secondPlayerListener = new Thread(new SecondPlayerListener());

    private Socket socketFirst;
    private Socket socketSecond;
    private ServerSocket mainSocket;
    private Scanner scannerFirst;
    private Scanner scannerSecond;
    private PrintWriter printWriterFirst;
    private PrintWriter printWriterSecond;
    private Model modelFirst;
    private Model modelSecond;
    private Timeline timeLine;

    public MainThread(Socket socketFirst, Socket socketSecond, ServerSocket mainSocket) {
        this.socketFirst = socketFirst;
        this.socketSecond = socketSecond;
        this.mainSocket = mainSocket;
    }

    @Override
    public void run() {

        try {
            scannerFirst = new Scanner(socketFirst.getInputStream());
            scannerSecond = new Scanner(socketSecond.getInputStream());
            printWriterFirst = new PrintWriter(socketFirst.getOutputStream(), true);
            printWriterSecond = new PrintWriter(socketSecond.getOutputStream(), true);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        printWriterFirst.println("Start");
        printWriterSecond.println("Start");
        firstPlayerListener.start();
        secondPlayerListener.start();

        Ball ballFirst = new Ball(width / 2, height);
        Ball ballSecond = new Ball(width / 2, 0);

        timeLine = new Timeline(new KeyFrame(Duration.millis(10), e -> nextStep(ballFirst, ballSecond)));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private class FirstPlayerListener implements Runnable {

        @Override
        public void run() {

            modelFirst = new Model();

            while (scannerFirst.hasNext()) {
                modelFirst.setPosY(Double.parseDouble(scannerFirst.nextLine()));
                printWriterSecond.println("oy:" + modelFirst.getPosY());
            }
        }
    }

    private class SecondPlayerListener implements Runnable {

        @Override
        public void run() {

            modelSecond = new Model();

            try {
                while (scannerSecond.hasNext()) {
                    modelSecond.setPosY(Double.parseDouble(scannerSecond.nextLine()));
                    printWriterFirst.println("oy:" + modelSecond.getPosY());
                }
            } catch (IndexOutOfBoundsException e) {
                modelSecond.setPosY(height);
            }
        }
    }

    private void nextStep(Ball ballFirst, Ball ballSecond) {

        ballFirst.setPosX(ballFirst.getPosX() + 10);
        ballFirst.setPosY(ballFirst.getPosY() + (ballFirst.getPosX() - 400) * 0.015);
        ballSecond.setPosX(ballSecond.getPosX() - 10);
        ballSecond.setPosY(ballSecond.getPosY() + (ballFirst.getPosX() - 400) * 0.015);

        printWriterFirst.println("bxF:" + ballFirst.getPosX());
        printWriterFirst.println("bxS:" + ballSecond.getPosX());
        printWriterSecond.println("bxF:" + (width - ballFirst.getPosX()));
        printWriterSecond.println("bxS:" + (width - ballSecond.getPosX()));
        printWriterFirst.println("byF:" + ballFirst.getPosY());
        printWriterFirst.println("byS:" + ballSecond.getPosY());
        printWriterSecond.println("byF:" + ballFirst.getPosY());
        printWriterSecond.println("byS:" + ballSecond.getPosY());

        if (ballFirst.getPosX() >= 1000) {
            ballFirst.setPosY(modelFirst.getPosY());
            ballFirst.setPosX(0);
        }

        if (ballSecond.getPosX() <= 0) {
            ballSecond.setPosY(modelSecond.getPosY());
            ballSecond.setPosX(height);
        }

        boolean playerF = false;
        boolean playerS = false;

        if (ballFirst.getPosX() > height - modelSecond.WIDTH - ballFirst.RADIUS / 2 && ballFirst.getPosY() < modelSecond.getPosY() + modelSecond.HEIGHT / 2 + ballFirst.RADIUS / 2 && ballFirst.getPosY() > modelSecond.getPosY() - modelSecond.HEIGHT / 2 - ballFirst.RADIUS / 2) {
            playerF = true;
        }

        if (ballSecond.getPosX() < modelFirst.WIDTH + ballFirst.RADIUS / 2 && ballSecond.getPosY() < modelFirst.getPosY() + modelFirst.HEIGHT / 2 + ballSecond.RADIUS / 2 && ballSecond.getPosY() > modelFirst.getPosY() - modelFirst.HEIGHT / 2 - ballSecond.RADIUS / 2) {
            playerS = true;
        }

        if (playerS && playerF) {
            printWriterFirst.write("go:DRAW!");
            printWriterSecond.write("go:DRAW!");
            endGame();

        } else {

            if (playerF) {
                printWriterFirst.write("go:You win!");
                printWriterSecond.write("go:You lose!");
            }

            if (playerS) {
                printWriterSecond.write("go:You win!");
                printWriterFirst.write("go:You lose!");
            }
        }
    }

    private void endGame() {

        try {

            timeLine.stop();
            mainSocket.close();

            firstPlayerListener.interrupt();
            secondPlayerListener.interrupt();

            socketFirst.close();
            socketSecond.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
