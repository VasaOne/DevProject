package com.Graphics.Workspace;

import com.Config;

import java.util.ArrayList;
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

    /**
     * The components that the sheet contains
     */
    ArrayList<ComponentInstance> components;
    /**
     * The nodes that the sheet contains
     */
    ArrayList<GraphicNode> nodes;
    /**
     * The real wires that the sheet contains
     */
    ArrayList<WireInstance> wires;

    public IOComponent ioComponent;

    public Sheet(double width, double height) {
        this.height = height;
        this.width = width;

        components = new ArrayList<>();
        nodes = new ArrayList<>();
        wires = new ArrayList<>();

        ioComponent = new IOComponent();
    }

    public void addObject(ComponentInstance instance) {
        instance.isPlaced = true;
        components.add(instance);
        nodes.addAll(instance.getAllNodes());
    }
    public void addWire(WireInstance wire) {
        wires.add(wire);
    }
    public void addOrphanNode(GraphicNode node) {
        nodes.add(node);
    }

    public void removeWire(WireInstance wire) {
        while (wires.remove(wire)) {}
    }

    /**
     * Gets the node
     * @param xNU the x coordinate
     * @param yNU the y coordinate
     * @return null if not node, else returns the node
     */
    public GraphicNode getNodeAt(double xNU, double yNU) {
        for (GraphicNode node: nodes) {
            if (Math.pow(xNU - node.getCenterX(), 2) + Math.pow(yNU - node.getCenterY(), 2) <= 0.25d) {
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
                    componentToMove.getOriginX() <= componentOnSheet.getOriginX() + componentOnSheet.instanceOf.getWidth() + Config.WSDistBtwCompo + 1 &&
                    componentOnSheet.getOriginX() <= componentToMove.getOriginX() + componentToMove.instanceOf.getWidth() + Config.WSDistBtwCompo + 1 &&
                    componentToMove.getOriginY() <= componentOnSheet.getOriginY() + componentOnSheet.instanceOf.getHeight() + Config.WSDistBtwCompo &&
                    componentOnSheet.getOriginY() <= componentToMove.getOriginY() + componentToMove.instanceOf.getHeight() + Config.WSDistBtwCompo
            ) {
                return componentOnSheet;
            }
        }
        return null;
    }
    public boolean isWireOverriding(WireInstance wire) {
        for (ComponentInstance componentOnSheet: components) {
            boolean isAroundWire = (Objects.nonNull(wire.getStart()) && !componentOnSheet.equals(wire.getStart().relativeTo) || Objects.isNull(wire.getStart())) &&
                    (Objects.nonNull(wire.getEnd()) && !componentOnSheet.equals(wire.getEnd().relativeTo) || Objects.isNull(wire.getEnd()));
            if (isAroundWire && (
                    (wire.getStartX() <= componentOnSheet.getOriginX() + componentOnSheet.instanceOf.getWidth() + Config.WSDistBtwCompo + 1 &&
                    componentOnSheet.getOriginX() <= wire.getMiddle() + Config.WSDistBtwCompo + 1 &&
                    wire.getStartY() <= componentOnSheet.getOriginY() + componentOnSheet.instanceOf.getHeight() + Config.WSDistBtwCompo &&
                    componentOnSheet.getOriginY() <= wire.getStartY() + Config.WSDistBtwCompo) ||
                    (wire.getMiddle() <= componentOnSheet.getOriginX() + componentOnSheet.instanceOf.getWidth() + Config.WSDistBtwCompo + 1 &&
                    componentOnSheet.getOriginX() <= wire.getMiddle() + Config.WSDistBtwCompo + 1 &&
                    wire.getStartY() <= componentOnSheet.getOriginY() + componentOnSheet.instanceOf.getHeight() + Config.WSDistBtwCompo &&
                    componentOnSheet.getOriginY() <= wire.getEndY() + Config.WSDistBtwCompo) ||
                    (wire.getMiddle() <= componentOnSheet.getOriginX() + componentOnSheet.instanceOf.getWidth() + Config.WSDistBtwCompo + 1 &&
                    componentOnSheet.getOriginX() <= wire.getEndX() + Config.WSDistBtwCompo + 1 &&
                    wire.getEndY() <= componentOnSheet.getOriginY() + componentOnSheet.instanceOf.getHeight() + Config.WSDistBtwCompo &&
                    componentOnSheet.getOriginY() <= wire.getEndY() + Config.WSDistBtwCompo))
            ) {
                return true;
            }
        }
        return false;
    }
    public boolean areWiresOverriding() {
        for (WireInstance wire: wires) {
            if (isWireOverriding(wire)) {
                return true;
            }
        }
        return false;
    }
    public boolean isThereAWire(OutputNode start, InputNode end) {
        //On regarde simplement si le début du fil relié à end est start
        return Objects.equals(end.wireConnected.getStart(), start);
    }
}
