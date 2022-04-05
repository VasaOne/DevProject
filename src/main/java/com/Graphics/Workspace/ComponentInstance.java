package com.Graphics.Workspace;

import com.Config;
import com.Physics.Component;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComponentInstance extends ObjectInstance {
    public InputNode[] inputs;
    public OutputNode[] outputs;

    boolean isPlaced = false;
    boolean canBePlaced = true;
    private boolean isSelected = false;

    public ComponentInstance(SheetObject object) {
        instanceOf = object;
        inputs = new InputNode[instanceOf.inputs];
        outputs = new OutputNode[instanceOf.outputs];
        for (int i = 0; i < object.inputs; i++) {
            inputs[i] = new InputNode(this, 0, instanceOf.inputNodeHeights[i]);
        }
        for (int i = 0; i < object.outputs; i++) {
            outputs[i] = new OutputNode(this, 0, instanceOf.outputNodeHeights[i]);
        }
        physicComponent = new Component(object.name, object.inputs, object.outputs, object.truthTable);
    }

    public ComponentInstance(SheetObject object, double originX, double originY) {
        this(object);
        setOriginX(originX);
        setOriginY(originY);
        physicComponent = new Component(object.name, object.inputs, object.outputs, object.truthTable);
    }

    /**
     * Gets the X coordinate of the component instance modified by the selection state
     * @return the X coordinate of the component instance
     */
    public double getOriginX() {
        return super.getOriginX(); //isSelected ? super.getOriginX() - Config.WSCompoSelectedSize / 2 : super.getOriginX();
    }
    /**
     * Gets the Y coordinate of the component instance modified by the selection state
     * @return the Y coordinate of the component instance
     */
    public double getOriginY() {
        return super.getOriginY(); //isSelected ? super.getOriginY() - Config.WSCompoSelectedSize / 2 : super.getOriginY();
    }

    /**
     * Gets the width of the component instance modified by the selection state
     * @return the width of the component instance
     */
    public double getWidth() {
        return /*instanceOf.getWidth(); */isSelected ? super.getWidth() + Config.WSCompoSelectedSize : super.getWidth();
    }
    /**
     * Gets the height of the component instance modified by the selection state
     * @return the height of the component instance
     */
    public double getHeight() {
        return /*instanceOf.getHeight();*/ isSelected ? super.getHeight() + Config.WSCompoSelectedSize : super.getHeight();
    }

    /**
     * Get all the nodes of this component instance
     * @return the ArrayList of all the nodes
     */
    public ArrayList<GraphicNode> getAllNodes() {
        ArrayList<GraphicNode> nodes = new ArrayList(List.of(inputs));
        nodes.addAll(List.of(outputs));
        return nodes;
    }
    /**
     * Get all the ON nodes of this component instance
     * @return the ArrayList of all the ON nodes
     */
    public ArrayList<GraphicNode> getOnNodes() {
        ArrayList<GraphicNode> onNodes = new ArrayList<>();
        for (GraphicNode node: inputs) {
            if (node.getState()) {
                onNodes.add(node);
            }
        }
        for (GraphicNode node: outputs) {
            if (node.getState()) {
                onNodes.add(node);
            }
        }
        return onNodes;
    }

    /**
     * Get all the OFF nodes of this component instance
     * @return the ArrayList of all the OFF nodes
     */
    public ArrayList<GraphicNode> getOffNodes() {
        ArrayList<GraphicNode> offNodes = new ArrayList<>();
        for (GraphicNode node: inputs) {
            if (!node.getState()) {
                offNodes.add(node);
            }
        }
        for (GraphicNode node: outputs) {
            if (!node.getState()) {
                offNodes.add(node);
            }
        }
        return offNodes;
    }

    /**
     * Draws the component instance
     * @param context the graphics context where to draw
     * @param scale the scale of the workspace
     */
    void drawComponent(GraphicsContext context, double scale) {
        // Determines the color of the component
        if (canBePlaced) {
            context.setFill(instanceOf.color);
        }
        else {
            context.setFill(Config.WSDisabledColor);
        }

        // Draws the component
        context.fillRoundRect(
                getOriginX() * scale, getOriginY() * scale,
                getWidth() * scale, getHeight() * scale,
                Config.WSComponentRoundSize * scale, Config.WSComponentRoundSize * scale
        );
        // Draws the name of the component
        context.setFill(Config.WSTextColor);
        context.fillText(instanceOf.name, getCenterX() * scale, getCenterY() * scale);

        // Draws the nodes
        for (GraphicNode node: inputs) {
            node.drawNode(context, scale);
        }
        for (GraphicNode node: outputs) {
            node.drawNode(context, scale);
        }
    }

    /**
     * Checks if the wires connected to this component instance are long enough
     * @return true if the wires are long enough, false otherwise
     */
    boolean areWiresFacing() {
        return areInputWiresLongEnough() && areOutputWiresLongEnough();
    }

    /**
     * Checks if the input wires connected to this component instance are long enough
     * @return true if the input wires are long enough, false otherwise
     */
    private boolean areInputWiresLongEnough() {
        //On regarde chaque input
        for (InputNode node: inputs) {
            //Si l'input est relié à un fil, mais que ce fil n'est pas assez long
            if (node.wireConnected.isReal && !node.wireConnected.isWidthLarge()) {
                return false;
            }
        }
        return true;
    }
    /**
     * Checks if the output wires connected to this component instance are long enough
     * @return true if the output wires are long enough, false otherwise
     */
    private boolean areOutputWiresLongEnough() {
        //On regarde chaque ouput
        for (OutputNode node: outputs) {
            //Pour tous les fils reliés à l'output
            for (int i = 1; i < node.wiresConnected.size(); i++) {
                //Si le fil n'est pas assez long, on renvoit false
                if (!node.wiresConnected.get(i).isWidthLarge()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the component instance will be placed inside the workspace
     * @param width the width of the workspace
     * @param height the height of the workspace
     * @return true if the component instance will be placed inside the workspace, false otherwise
     */
    public boolean isOnSheet(double width, double height) {
        return getOriginX() > Config.WSDistBtwCompo + 0.5 && getOriginY() > Config.WSDistBtwCompo &&
                getOriginX() + this.getWidth() < width - Config.WSDistBtwCompo - 0.5 &&
                getOriginY() + this.getHeight() < height - Config.WSDistBtwCompo;
    }

    /**
     * Sets the component selection state
     * @param selected the new selection state
     */
    public void selectComponent(boolean selected) {
        double centerX = getCenterX();
        double centerY = getCenterY();
        this.isSelected = selected;
        setCenter(centerX, centerY);
    }

    /**
     * Gets the component selection state
     * @return true if the component is selected, false otherwise
     */
    public boolean isSelected() {
        return isSelected;
    }
}
