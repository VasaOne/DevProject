package com.Graphics.Workspace;

import com.Config;
import com.Physics.Component;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComponentInstance extends ObjectInstance {
    public NodeInstance[] inputs;
    public NodeInstance[] outputs;

    boolean isPlaced = false;
    boolean canBePlaced = true;

    public ComponentInstance(SheetObject object) {
        instanceOf = object;
        inputs = new NodeInstance[instanceOf.inputs];
        outputs = new NodeInstance[instanceOf.outputs];
        for (int i = 0; i < object.inputs; i++) {
            inputs[i] = new NodeInstance(this, false, 0, instanceOf.inputNodeHeights[i]);
        }
        for (int i = 0; i < object.outputs; i++) {
            outputs[i] = new NodeInstance(this, true, instanceOf.getWidth(), instanceOf.outputNodeHeights[i]);
        }
        physicComponent = new Component(object.name, object.inputs, object.outputs, object.truthTable);
    }

    public ComponentInstance(SheetObject object, double originX, double originY) {
        this(object);
        this.originX = originX;
        this.originY = originY;
        physicComponent = new Component(object.name, object.inputs, object.outputs, object.truthTable);
    }

    public ArrayList<NodeInstance> getAllNodes() {
        ArrayList<NodeInstance> nodes = new ArrayList(List.of(inputs));
        nodes.addAll(List.of(outputs));
        return nodes;
    }
    public ArrayList<NodeInstance> getOnNodes() {
        ArrayList<NodeInstance> onNodes = new ArrayList<>();
        for (NodeInstance node: inputs) {
            if (node.getState()) {
                onNodes.add(node);
            }
        }
        for (NodeInstance node: outputs) {
            if (node.getState()) {
                onNodes.add(node);
            }
        }
        return onNodes;
    }
    public ArrayList<NodeInstance> getOffNodes() {
        ArrayList<NodeInstance> offNodes = new ArrayList<>();
        for (NodeInstance node: inputs) {
            if (!node.getState()) {
                offNodes.add(node);
            }
        }
        for (NodeInstance node: outputs) {
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

        for (NodeInstance node: getOnNodes()) {
            context.setFill(Config.WSOnNodesColor);
            context.fillOval(
                    node.getOriginX() * scale,
                    node.getOriginY() * scale,
                    node.instanceOf.getWidth() * scale,
                    node.instanceOf.getHeight() * scale
            );
        }
        for (NodeInstance node: getOffNodes()) {
            context.setFill(Config.WSOffNodesColor);
            context.fillOval(
                    node.getOriginX() * scale,
                    node.getOriginY() * scale,
                    node.instanceOf.getWidth() * scale,
                    node.instanceOf.getHeight() * scale
            );
        }
    }

    void moveComponent(double toXNU, double toYNU) {
        originX = toXNU;
        originY = toYNU;
    }

    boolean areWiresFacing() {
        return !testWires(inputs) && !testWires(outputs);
    }

    private boolean testWires(NodeInstance[] outputs) {
        for (NodeInstance node: outputs) {
            WireInstance wire = node.getWire();
            if (Objects.nonNull(wire) && wire.isReal) {
                if (!wire.isWidthLarge()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOnSheet(double width, double height) {
        return originX > Config.WSDistBtwCompo + 0.5 && originY > Config.WSDistBtwCompo &&
                originX + this.instanceOf.getWidth() < width - Config.WSDistBtwCompo - 0.5 &&
                originY + this.instanceOf.getHeight() < height - Config.WSDistBtwCompo;
    }
}
