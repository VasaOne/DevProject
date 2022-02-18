package com.Graphics.Canvas;

import java.util.ArrayList;

public class ComponentInstance extends ObjectInstance {
    public ArrayList<NodeInstance> nodes;

    public ComponentInstance(SheetObject object) {

    }

    public ArrayList<NodeInstance> getOnNodes() {
        ArrayList<NodeInstance> onNodes = new ArrayList<>();
        for (NodeInstance node: nodes) {
            if (node.state) {
                onNodes.add(node);
            }
        }
        return onNodes;
    }
    public ArrayList<NodeInstance> getOffNodes() {
        ArrayList<NodeInstance> offNodes = new ArrayList<>();
        for (NodeInstance node: nodes) {
            if (!node.state) {
                offNodes.add(node);
            }
        }
        return offNodes;
    }
}
