package com.Graphics.Workspace;

import com.Config;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class ComponentInstance extends ObjectInstance {
    NodeInstance[] inputs;
    NodeInstance[] outputs;

    boolean isPlaced = false;
    boolean canBePlaced = true;

    public ComponentInstance(SheetObject object) {
        instanceOf = object;
        inputs = new NodeInstance[instanceOf.inputs];
        outputs = new NodeInstance[instanceOf.outputs];
        for (int i = 0; i < object.inputs; i++) {
            inputs[i] = new NodeInstance(this, 0, instanceOf.inputNodeHeights[i]);
        }
        for (int i = 0; i < object.outputs; i++) {
            outputs[i] = new NodeInstance(this, instanceOf.getWidth(), instanceOf.outputNodeHeights[i]);
        }
    }

    public ComponentInstance(SheetObject object, double originX, double originY) {
        this(object);
        this.originX = originX;
        this.originY = originY;
    }

    public ArrayList<NodeInstance> getAllNodes() {
        ArrayList<NodeInstance> nodes = new ArrayList(List.of(inputs));
        nodes.addAll(List.of(outputs));
        return nodes;
    }
    public ArrayList<NodeInstance> getOnNodes() {
        ArrayList<NodeInstance> onNodes = new ArrayList<>();
        for (NodeInstance node: inputs) {
            if (node.state) {
                onNodes.add(node);
            }
        }
        for (NodeInstance node: outputs) {
            if (node.state) {
                onNodes.add(node);
            }
        }
        return onNodes;
    }
    public ArrayList<NodeInstance> getOffNodes() {
        ArrayList<NodeInstance> offNodes = new ArrayList<>();
        for (NodeInstance node: inputs) {
            if (!node.state) {
                offNodes.add(node);
            }
        }
        for (NodeInstance node: outputs) {
            if (!node.state) {
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

        context.fillRect(
                getOriginX() * scale, getOriginY() * scale,
                instanceOf.getWidth() * scale, instanceOf.getHeight() * scale
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
}
