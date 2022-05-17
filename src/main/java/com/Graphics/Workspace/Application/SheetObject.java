package com.Graphics.Workspace.Application;

import com.Config;
import javafx.scene.paint.Color;

/**
 * This class represents a theoretical object
 */
public class SheetObject {
    public int id;
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

    public Boolean[] truthTable;

    private SheetObject() {}

    public SheetObject(int id, String name, Color color, int inputs, int outputs) {
        this.id = id;
        this.name = name;

        this.color = color;
        this.inputs = inputs;
        this.outputs = outputs;

        width = 0.60 * (name.length() + 5);
        height = Math.max(inputs - 1, outputs - 1) * Config.WSNodeSpace + 2 * Config.WSOutSpace;

        inputNodeHeights = new double[inputs];
        for (int i = 0; i < inputs; i++) {
            inputNodeHeights[i] = Config.WSNodeSpace * i + (height - Config.WSNodeSpace * (inputs - 1)) / 2d;
        }
        outputNodeHeights = new double[outputs];
        for (int i = 0; i < outputs; i++) {
            outputNodeHeights[i] = Config.WSNodeSpace * i + (height - Config.WSNodeSpace * (outputs - 1)) / 2d;
        }
    }

    public SheetObject(int id, String name, Color color, int inputs, int outputs, Boolean[] truthTable) {
        this(id, name, color, inputs, outputs);

        this.truthTable = truthTable;
    }

    public static SheetObject DefaultNode() {
        SheetObject node = new SheetObject();
        node.height = 1;
        node.width = 1;
        return node;
    }
}
