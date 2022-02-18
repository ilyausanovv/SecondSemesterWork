package com.usanov.models;

import javafx.scene.image.ImageView;

public class Model {

    public final int HEIGHT = 100;
    public final int WIDTH = 50;
    private double posY;
    private double posX;

    public Model() {
    }

    public Model(double posY) {
        this.posY = posY;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }
}