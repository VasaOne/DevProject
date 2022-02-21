package com.Graphics.Canvas;

import com.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ComponentInstance extends ObjectInstance {
    NodeInstance[] inputs;
    NodeInstance[] outputs;

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

    void DrawComponent(GraphicsContext context, double scale) {
        context.setFill(instanceOf.color);
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
}
