package com.Graphics.Workspace.Node;

import com.Config;
import com.Graphics.Workspace.Component.ObjectInstance;
import com.Graphics.Workspace.Wire.WireInstance;
import javafx.scene.canvas.GraphicsContext;

public abstract class GraphicNode {
    public ObjectInstance relativeTo;
    public boolean isGlobal;

    protected WireInstance abstractWire;

    public GraphicNode(ObjectInstance relativeTo, double centerX, double centerY) {
        this.relativeTo = relativeTo;
        this.originX = centerX - 0.5;
        this.originY = centerY - 0.5;
    }

    public abstract boolean getState();
    public abstract void setState(boolean state);

    /**
     * The position of the node relative to the component
     */
    double originX;
    /**
     * The position of the node relative to its component
     */
    double originY;

    /**
     * The position on the sheet relative to the origin, in NU
     */
    public double getOriginX() {
        return relativeTo.getOriginX() + originX;
    }
    /**
     * The position on the sheet relative to the origin, in NU
     */
    public double getOriginY() {
        return relativeTo.getOriginY() + originY;
    }

    /**
     * Sets the origin of the node
     * @param originX the new X origin
     */
    public void setOriginX(double originX) {
        this.originX = originX;
    }

    /**
     * Sets the origin of the node
     * @param originY the new Y origin
     */
    public void setOriginY(double originY) {
        this.originY = originY;
    }

    /**
     * The position on the sheet relative to the origin, in NU
     */
    public double getCenterX() {
        return getOriginX() + getSize() / 2;
    }
    /**
     * The position on the sheet relative to the origin, in NU
     */
    public double getCenterY() {
        return getOriginY() + getSize() / 2;
    }

    public double getSize() {
        return 1;//relativeTo.isSelected() ? Config.WSCompoSelectedSize + 1 : 1;
    }

    /**
     * Draws the node on the workspace
     * @param context the graphics context
     * @param scale the scale of the workspace
     */
    public void drawNode(GraphicsContext context, double scale) {
        if (getState()) {
            context.setFill(Config.WSOnNodesColor);
        }
        else {
            context.setFill(Config.WSOffNodesColor);
        }
        context.fillOval(getOriginX() * scale, getOriginY() * scale, getSize() * scale, getSize() * scale);
    }
//
//    public void drawNode(GraphicsContext context, double scale, double xModifier) {
//        if (getState()) {
//            context.setFill(Config.WSOnNodesColor);
//        }
//        else {
//            context.setFill(Config.WSOffNodesColor);
//        }
//        context.fillOval(getOriginX() * xModifier, getOriginY() * scale, scale, scale);
//    }
}
