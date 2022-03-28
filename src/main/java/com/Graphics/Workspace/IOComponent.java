package com.Graphics.Workspace;

import com.Config;
import com.Physics.Component;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class IOComponent extends ObjectInstance {
    public ArrayList<NodeInstance> nodes;

    public boolean isInput;

    public double xPos;
    public double height;

    private Component physicComponent;

    public IOComponent(boolean isInput, double xPos) {
        nodes = new ArrayList<>();

        this.isInput = isInput;

        String name = isInput ? "Input" : "Output";
        this.xPos = xPos;
        //physicComponent = new Component(name, , object.outputs, object.truthTable);
    }

    public void addNode(Sheet sheet) {
        NodeInstance node = new NodeInstance(this, isInput, xPos, 0, isInput);
        nodes.add(node);
        height = sheet.height;
        recalculateNodePos();
        sheet.addOrphanNode(node);
    }

    public NodeInstance delNode(int index, Sheet sheet) {
        NodeInstance node = nodes.remove(index);
        recalculateNodePos();
        sheet.nodes.remove(node);
        return node;
    }

    private void recalculateNodePos() {
        int nodesNb = nodes.size();
        for (int i = 0; i < nodesNb; i++) {
            nodes.get(i).setOriginY(height * (i + 1) / (nodesNb + 1) + 1/2);
        }
    }

    public void drawComponent(GraphicsContext context, double scale, double height) {
        for (NodeInstance node: nodes) {
            if (node.getState()) {
                context.setFill(Config.WSOnNodesColor);
            }
            else {
                context.setFill(Config.WSOffNodesColor);
            }
            context.fillOval(
                    node.getOriginX() * scale,
                    node.getOriginY() * scale,
                    node.instanceOf.getWidth() * scale,
                    node.instanceOf.getHeight() * scale
            );
        }
    }
}
