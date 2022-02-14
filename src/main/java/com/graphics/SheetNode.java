package com.graphics;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SheetNode extends SheetObject {
    int radius;
    int offsetX;
    int offsetY;
    boolean state;
    Color onColor;

    SheetNode(Color offColor, Color onColor, int radius, int posX, int posY) {
        super(new Circle(radius, offColor), offColor, radius * 2, radius * 2, posX, posY);

        this.radius = radius;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        state = false;
        this.onColor = onColor;
    }

    @Override
    void drawShape(Pane pane) {

    }
}
