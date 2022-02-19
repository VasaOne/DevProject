package com.Graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * A sheet which will contain our components and wires
 */
public class Sheet {
    /**
     * Defines in node units (NU) the height of this sheet
     */
    double height;
    /**
     * Defines in node units (NU) the width of this sheet
     */
    double width;

    ArrayList<ComponentInstance> objects;
    ArrayList<WireInstance> wires;

    public Sheet(double height, double width) {
        this.height = height;
        this.width = width;

        objects = new ArrayList<>();
        wires = new ArrayList<>();
    }

    public void addObject(ComponentInstance instance) {
        objects.add(instance);
    }
    public void addWire(WireInstance wire) {
        wires.add(wire);
    }
}
