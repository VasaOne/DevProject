package com.graphics;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class SheetObject {
    protected Shape shape;
    protected Color color;
    protected int sizeX;
    protected int sizeY;

    protected int posX;
    int getPosX() {
        return posX;
    }
    protected int posY;
    int getPosY() {
        return posY;
    }

    protected SheetObject(Shape shape, Color color, int sizeX, int sizeY, int posX, int posY) {
        this.shape = shape;
        this.color = color;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.posX = posX;
        this.posY = posY;
    }

    void drawShape(Pane pane) {

    }
}
