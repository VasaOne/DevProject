package com.Graphics.Canvas;

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
            outputs[i].state = true;
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
}
