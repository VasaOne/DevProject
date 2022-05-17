package com.Graphics.Workspace.Sheet;

import com.Config;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Graphics.Workspace.Component.IOComponent;
import com.Graphics.Workspace.Node.GraphicNode;
import com.Graphics.Workspace.Node.InputNode;
import com.Graphics.Workspace.Node.OutputNode;
import com.Graphics.Workspace.Wire.WireInstance;
import com.Graphics.Workspace.Wire.WireInteraction;

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
     * Gets the Width in node units (NU) of the sheet
     * @return the width
     */
    public double getWidth() {
        return width;
    }
    /**
     * Gets the Height in node units (NU) of the sheet
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * The components that the sheet contains
     */
    public ArrayList<ComponentInstance> components;
    /**
     * The nodes that the sheet contains
     */
    public ArrayList<GraphicNode> nodes;
    /**
     * The real wires that the sheet contains
     */
    public ArrayList<WireInstance> wires;

    public IOComponent ioComponent;

    /**
     * Creates a new sheet with the given width and height
     * @param width the width of the sheet
     * @param height the height of the sheet
     */
    public Sheet(double width, double height) {
        this.height = height;
        this.width = width;

        components = new ArrayList<>();
        nodes = new ArrayList<>();
        wires = new ArrayList<>();

        ioComponent = new IOComponent(this);
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
        wire.remove();
        while (true) {
            if (!wires.remove(wire)) break;
        }
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
                    xNU <= component.getOriginX() + component.getWidth() &&
                    component.getOriginY() <= yNU &&
                    yNU <= component.getOriginY() + component.getHeight()
            ) {
                return component;
            }
        }
        return null;
    }

    public WireInteraction getWireAt(double xNU, double yNU) {
        for (WireInstance wire: wires) {
            if (wire.wireInteraction.hasTouchedWire(xNU, yNU)) {
                return wire.wireInteraction;
            }
        }
        return null;
    }

    public ComponentInstance isOverriding(ComponentInstance componentToMove) {
        for (ComponentInstance componentOnSheet: components) {
            if (componentOnSheet.isPlaced &&
                    componentToMove.getOriginX() <= componentOnSheet.getOriginX() + componentOnSheet.getWidth() + Config.WSDistBtwCompo + 1 &&
                    componentOnSheet.getOriginX() <= componentToMove.getOriginX() + componentToMove.getWidth() + Config.WSDistBtwCompo + 1 &&
                    componentToMove.getOriginY() <= componentOnSheet.getOriginY() + componentOnSheet.getHeight() + Config.WSDistBtwCompo &&
                    componentOnSheet.getOriginY() <= componentToMove.getOriginY() + componentToMove.getHeight() + Config.WSDistBtwCompo
            ) {
                return componentOnSheet;
            }
        }
        return null;
    }
    public boolean isWireOverridingComponent(WireInstance wire, ComponentInstance component) {
        return (Objects.nonNull(wire.getStart()) && !Objects.equals(component, wire.getStart().relativeTo) || Objects.isNull(wire.getStart())) &&
                (Objects.nonNull(wire.getEnd()) && !Objects.equals(component, wire.getEnd().relativeTo) || Objects.isNull(wire.getEnd())) &&
                ((wire.getStartX() <= component.getOriginX() + component.getWidth() + Config.WSDistBtwCompo + 1 &&
                    component.getOriginX() <= wire.getMiddle() + Config.WSDistBtwCompo + 1 &&
                    wire.getStartY() <= component.getOriginY() + component.getHeight() + Config.WSDistBtwCompo &&
                    component.getOriginY() <= wire.getStartY() + Config.WSDistBtwCompo) ||
                    (wire.getMiddle() <= component.getOriginX() + component.getWidth() + Config.WSDistBtwCompo + 1 &&
                    component.getOriginX() <= wire.getMiddle() + Config.WSDistBtwCompo + 1 &&
                    wire.getStartY() <= component.getOriginY() + component.getHeight() + Config.WSDistBtwCompo &&
                    component.getOriginY() <= wire.getEndY() + Config.WSDistBtwCompo) ||
                    (wire.getMiddle() <= component.getOriginX() + component.getWidth() + Config.WSDistBtwCompo + 1 &&
                    component.getOriginX() <= wire.getEndX() + Config.WSDistBtwCompo + 1 &&
                    wire.getEndY() <= component.getOriginY() + component.getHeight() + Config.WSDistBtwCompo &&
                    component.getOriginY() <= wire.getEndY() + Config.WSDistBtwCompo)
                );
    }
    public boolean isWireOverriding(WireInstance wire) {
        for (ComponentInstance componentOnSheet: components) {
            if (isWireOverridingComponent(wire, componentOnSheet)) {
                //System.out.println(componentOnSheet.instanceOf.name);
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

    /**
     * Collect data from the component and put it in ComponentData
     * @param data the storage of the data
     */
    public void dataCollector(ComponentData data) {
        data.inputs = ioComponent.startNodes.size();
        data.outputs = ioComponent.endNodes.size();
        data.width = width;
        data.height = height;

        data.components = new int[components.size()];
        data.componentsX = new double[components.size()];
        data.componentsY = new double[components.size()];
        for (int i = 0; i < components.size(); i++) {
            data.components[i] = components.get(i).instanceOf.id;
            data.componentsX[i] = components.get(i).getOriginX();
            data.componentsY[i] = components.get(i).getOriginY();
        }

        data.wiresStartComp = new int[wires.size()];
        data.wiresStartNode = new int[wires.size()];
        data.wiresEndComp = new int[wires.size()];
        data.wiresEndNode = new int[wires.size()];
        data.wiresMiddle = new double[wires.size()];
        for (int i = 0; i < wires.size(); i++) {
            OutputNode start = wires.get(i).getStart();
            if (Objects.nonNull(start)) {
                if (start.relativeTo instanceof IOComponent) {
                    data.wiresStartComp[i] = -1;
                    data.wiresStartNode[i] = ((IOComponent) start.relativeTo).startNodes.indexOf(start);
                }
                else {
                    data.wiresStartComp[i] = components.indexOf(start.relativeTo);
                    data.wiresStartNode[i] = Arrays.asList(((ComponentInstance) start.relativeTo).outputs).indexOf(start);
                }
            }
            else {
                data.wiresStartComp[i] = -1;
                data.wiresStartNode[i] = -1;
                throw new IllegalStateException("Start node of wire " + i + " is null");
            }
            InputNode end = wires.get(i).getEnd();
            if (Objects.nonNull(end)) {
                if (end.relativeTo instanceof IOComponent) {
                    data.wiresEndComp[i] = -1;
                    data.wiresEndNode[i] = ((IOComponent) end.relativeTo).endNodes.indexOf(end);
                }
                else {
                    data.wiresEndComp[i] = components.indexOf(end.relativeTo);
                    data.wiresEndNode[i] = Arrays.asList(((ComponentInstance) end.relativeTo).inputs).indexOf(end);
                }
            }
            else {
                data.wiresEndComp[i] = -1;
                data.wiresEndNode[i] = -1;
                throw new IllegalStateException("End node of wire " + i + " is null");
            }
            data.wiresMiddle[i] = wires.get(i).getMiddle();
        }
    }
}
