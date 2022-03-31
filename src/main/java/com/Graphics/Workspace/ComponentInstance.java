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

    public ComponentInstance(SheetObject object) {
        instanceOf = object;
        inputs = new InputNode[instanceOf.inputs];
        outputs = new OutputNode[instanceOf.outputs];
        for (int i = 0; i < object.inputs; i++) {
            inputs[i] = new InputNode(this, 0, instanceOf.inputNodeHeights[i]);
        }
        for (int i = 0; i < object.outputs; i++) {
            outputs[i] = new OutputNode(this, instanceOf.getWidth(), instanceOf.outputNodeHeights[i]);
        }
        physicComponent = new Component(object.name, object.inputs, object.outputs, object.truthTable);
    }

    public ComponentInstance(SheetObject object, double originX, double originY) {
        this(object);
        this.originX = originX;
        this.originY = originY;
        physicComponent = new Component(object.name, object.inputs, object.outputs, object.truthTable);
    }

    public ArrayList<GraphicNode> getAllNodes() {
        ArrayList<GraphicNode> nodes = new ArrayList(List.of(inputs));
        nodes.addAll(List.of(outputs));
        return nodes;
    }
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

    void drawComponent(GraphicsContext context, double scale) {
        if (canBePlaced) {
            context.setFill(instanceOf.color);
        }
        else {
            context.setFill(Config.WSDisabledColor);
        }

        context.fillRoundRect(
                getOriginX() * scale, getOriginY() * scale,
                instanceOf.getWidth() * scale, instanceOf.getHeight() * scale,
                Config.WSComponentRoundSize * scale, Config.WSComponentRoundSize * scale
        );
        context.setFill(Config.WSTextColor);
        context.fillText(instanceOf.name, getCenterX() * scale, getCenterY() * scale);

        for (GraphicNode node: getAllNodes()) {
            node.drawNode(context, scale);
        }
    }

    void moveComponent(double toXNU, double toYNU) {
        originX = toXNU;
        originY = toYNU;
    }

    boolean areWiresFacing() {
        return areInputWiresLongEnough() && areOutputWiresLongEnough();
    }

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

    public boolean isOnSheet(double width, double height) {
        return originX > Config.WSDistBtwCompo + 0.5 && originY > Config.WSDistBtwCompo &&
                originX + this.instanceOf.getWidth() < width - Config.WSDistBtwCompo - 0.5 &&
                originY + this.instanceOf.getHeight() < height - Config.WSDistBtwCompo;
    }
}
