package com.Graphics.Canvas;

import com.Config;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.ConcurrentModificationException;

/**
 * This class represents a theoretical object
 */
public class SheetObject {
    static double nodeSpace = 1.4;
    static double outSpace = 1;

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

    private SheetObject() {}

    public SheetObject(String name, Color color, int inputs, int outputs) {
        this.name = name;

        this.color = color;
        this.inputs = inputs;
        this.outputs = outputs;

        width = 0.60 * (name.length() + 5);
        height = Math.max(inputs - 1, outputs - 1) * nodeSpace + 2 * outSpace;

        inputNodeHeights = new double[inputs];
        for (int i = 0; i < inputs; i++) {
            inputNodeHeights[i] = nodeSpace * i + (height - nodeSpace * (inputs - 1)) / 2d;
        }
        outputNodeHeights = new double[outputs];
        for (int i = 0; i < outputs; i++) {
            outputNodeHeights[i] = nodeSpace * i + (height - nodeSpace * (outputs - 1)) / 2d;
        }
    }

    public static SheetObject DefaultNode() {
        SheetObject node = new SheetObject();
        node.height = 1;
        node.width = 1;
        return node;
    }
}
