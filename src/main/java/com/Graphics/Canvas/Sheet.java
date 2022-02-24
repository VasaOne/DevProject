package com.Graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    double spaceBetween;

    ArrayList<ComponentInstance> components;
    ArrayList<NodeInstance> nodes;
    ArrayList<WireInstance> wires;

    public Sheet(double height, double width) {
        this.height = height;
        this.width = width;

        components = new ArrayList<>();
        nodes = new ArrayList<>();
        wires = new ArrayList<>();
    }

    public void addObject(ComponentInstance instance) {
        instance.isPlaced = true;
        components.add(instance);
        nodes.addAll(instance.getAllNodes());
    }
    public void addWire(WireInstance wire) {
        wires.add(wire);
    }

    public NodeInstance getNodeAt(double xNU, double yNU) {
        for (NodeInstance node: nodes) {
            if (Math.pow(xNU - node.getCenterX(), 2) + Math.pow(yNU - node.getCenterY(), 2) <= Math.pow(node.instanceOf.getWidth() / 2, 2)) {
                return node;
            }
        }
        return null;
    }

    public ComponentInstance getComponentAt(double xNU, double yNU) {
        for (ComponentInstance component: components) {
            if (component.isPlaced &&
                    component.getOriginX() <= xNU &&
                    xNU <= component.getOriginX() + component.instanceOf.getWidth() &&
                    component.getOriginY() <= yNU &&
                    yNU <= component.getOriginY() + component.instanceOf.getHeight()
            ) {
                return component;
            }
        }
        return null;
    }

    public ComponentInstance isOverriding(ComponentInstance componentToMove) {
        for (ComponentInstance componentOnSheet: components) {
            if (componentOnSheet.isPlaced &&
                    componentToMove.getOriginX() <= componentOnSheet.getOriginX() + componentOnSheet.instanceOf.getWidth() &&
                    componentOnSheet.getOriginX() <= componentToMove.getOriginX() + componentToMove.instanceOf.getWidth() &&
                    componentToMove.getOriginY() <= componentOnSheet.getOriginY() + componentOnSheet.instanceOf.getHeight() &&
                    componentOnSheet.getOriginY() <= componentToMove.getOriginY() + componentToMove.instanceOf.getHeight()
            ) {
                return componentOnSheet;
            }
        }
        return null;
    }
}
