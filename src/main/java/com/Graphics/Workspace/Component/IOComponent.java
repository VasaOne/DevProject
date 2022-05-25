package com.Graphics.Workspace.Component;

import com.Config;
import com.Graphics.Workspace.Node.GraphicNode;
import com.Graphics.Workspace.Node.InputNode;
import com.Graphics.Workspace.Node.OutputNode;
import com.Graphics.Workspace.Sheet.Sheet;
import com.Physics.Component;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class IOComponent extends ObjectInstance {
    public ArrayList<OutputNode> startNodes;
    public ArrayList<InputNode> endNodes;
    public Sheet sheet;

    private Component physicComponent;

    public IOComponent(Sheet sheet) {
        setOrigin(sheet.getWidth(), 0);
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
        return -sheet.getWidth();
    }

    /**
     * Gets the height of the iocomponent, which is the one of the sheet.
     * @return the height of the iocomponent
     */
    @Override
    public double getHeight() {
        return sheet.getHeight();
    }


    public void addStartNode(Sheet sheet) {
        OutputNode node = new OutputNode(this, 0, 0, startNodes.size());
        node.isGlobal = true;
        startNodes.add(node);
        recalculateNodePos(sheet);
        sheet.addOrphanNode(node);
    }
    public void addEndNode(Sheet sheet) {
        InputNode node = new InputNode(this, 0, 0, endNodes.size());
        node.isGlobal = true;
        endNodes.add(node);
        recalculateNodePos(sheet);
        sheet.addOrphanNode(node);
    }

    public void delStartNode(Sheet sheet) {
        for (OutputNode node: startNodes) {
            if (!node.hasWire()) {
                delStartNode(node, sheet);
                return;
            }
        }
        if (startNodes.size() > 0) {
            delStartNode(startNodes.get(0), sheet);
        }

    }
    private void delStartNode(OutputNode node, Sheet sheet) {
        while (node.hasWire()) {
            sheet.removeWire(node.wiresConnected.get(1));
        }
        startNodes.remove(node);
        sheet.nodes.remove(node);
        recalculateNodePos(sheet);
    }

    public void delEndNode(Sheet sheet) {
        for (InputNode node: endNodes) {
            if (!node.hasWire()) {
                delEndNode(node, sheet);
                return;
            }
        }
        if (endNodes.size() > 0) {
            delEndNode(endNodes.get(0), sheet);
        }
    }
    private void delEndNode(InputNode node, Sheet sheet) {
        sheet.removeWire(node.wireConnected);
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
        double startOff = (sheet.getHeight() - (startNodes.size() - 1) * Config.WSNodeSpace + 1) / 2;
        int i = 0;
        for (OutputNode startNode: startNodes) {
            startNode.setOriginX(-0.5);
            startNode.setOriginY(startOff + i * Config.WSNodeSpace);
            i++;
        }
        double endOff = (sheet.getHeight() - (endNodes.size() - 1) * Config.WSNodeSpace + 1) / 2;
        i = 0;
        for (InputNode endNode: endNodes) {
            endNode.setOriginX(-0.5);
            endNode.setOriginY(endOff + i * Config.WSNodeSpace);
            i++;
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
