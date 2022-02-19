package com.Graphics.Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * This class represents a theoretical object
 */
public class SheetObject {
    public Color color;
    public String name;

    public int inputs;
    public int outputs;

    public double[] inputNodeHeights;
    public double[] outputNodeHeights;

    private double height;
    private double width;

    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }

    private SheetObject() {};

    public SheetObject(String name, double textHeight, Color color, int inputs, int outputs) {
        this.name = name;

        this.color = color;
        this.inputs = inputs;
        this.outputs = outputs;

        width = textHeight * 0.7 * name.length() + 1;
        height = Math.max(inputs, outputs) * 1.2 + 2;

        inputNodeHeights = new double[inputs];
        for (int i = 0; i < inputs; i++) {
            inputNodeHeights[i] = 1 + 1.2 * i + (1.2 * (inputs - 1) ) / 2;
        }
        outputNodeHeights = new double[outputs];
        for (int i = 0; i < outputs; i++) {
            outputNodeHeights[i] = 1 + 1.2 * i + (1.2 * (outputs - 1) ) / 2;
        }
    }

    public static SheetObject DefaultNode() {
        SheetObject node = new SheetObject();
        node.height = 1;
        node.width = 1;
        return node;
    }
}
