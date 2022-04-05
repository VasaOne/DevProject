package com.Graphics.Workspace;

import com.Config;
import com.Physics.Component;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Objects;

public class IOComponent extends ObjectInstance {
    public ArrayList<OutputNode> startNodes;
    public ArrayList<InputNode> endNodes;
    public Sheet sheet;

    private Component physicComponent;

    public IOComponent(Sheet sheet) {
        setOrigin(sheet.width, 0);
        startNodes = new ArrayList<>();
        endNodes = new ArrayList<>();
        this.sheet = sheet;
    }

    /**
     * Gets the width of the iocomponent, which is the one of the sheet.
     * @return the width of the iocomponent
     */
    @Override
    public double getWidth() {
        return -sheet.width;
    }

    /**
     * Gets the height of the iocomponent, which is the one of the sheet.
     * @return the height of the iocomponent
     */
    @Override
    public double getHeight() {
        return sheet.height;
    }


    public void addStartNode(Sheet sheet) {
        OutputNode node = new OutputNode(this, 0, 0);
        node.isGlobal = true;
        startNodes.add(node);
        recalculateNodePos(sheet);
        sheet.addOrphanNode(node);
    }
    public void addEndNode(Sheet sheet) {
        InputNode node = new InputNode(this, 0, 0);
        endNodes.add(node);
        recalculateNodePos(sheet);
        sheet.addOrphanNode(node);
    }

    public void delStartNode(OutputNode node, Sheet sheet) {
        startNodes.remove(node);
        sheet.nodes.remove(node);
        recalculateNodePos(sheet);
    }
    public void delEndNode(InputNode node, Sheet sheet) {
        endNodes.remove(node);
        sheet.nodes.remove(node);
        recalculateNodePos(sheet);
    }
    public void delNode(GraphicNode node, Sheet sheet) {
        if (node instanceof OutputNode) {
            delStartNode((OutputNode)node , sheet);
        }
        else {
            delEndNode((InputNode)node, sheet);
        }
    }

    private void recalculateNodePos(Sheet sheet) {
        double startOff = (sheet.height - (startNodes.size() - 1) * Config.WSNodeSpace + 1) / 2;
        int i = 0;
        for (OutputNode startNode: startNodes) {
            startNode.originX = -0.5;
            startNode.originY = startOff + i * Config.WSNodeSpace;
            i++;
        }
        double endOff = (sheet.height - (endNodes.size() - 1) * Config.WSNodeSpace + 1) / 2;
        i = 0;
        for (InputNode endNode: endNodes) {
            endNode.originX =  -0.5;
            endNode.originY = endOff + i * Config.WSNodeSpace;
        }
    }

    public void drawComponent(GraphicsContext context, double scale) {
        for (OutputNode startNode: startNodes) {
            startNode.drawNode(context, scale);
        }
        for (InputNode endNode: endNodes) {
            endNode.drawNode(context, scale);
        }
    }
}
