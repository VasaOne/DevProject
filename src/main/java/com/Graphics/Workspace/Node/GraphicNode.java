package com.Graphics.Workspace.Node;

import com.Config;
import com.Graphics.Workspace.Component.ObjectInstance;
import com.Graphics.Workspace.Wire.WireInstance;
import javafx.scene.canvas.GraphicsContext;

public abstract class GraphicNode {
    public int id;

    public ObjectInstance relativeTo;
    public boolean isGlobal;

    protected WireInstance abstractWire;

    public GraphicNode(ObjectInstance relativeTo, double centerX, double centerY, int id) {
        this.relativeTo = relativeTo;
        this.id = id;
        setCenter(centerX, centerY);
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
     * Sets the center of the node
     * @param centerX the new X center
     */
    public void setCenterX(double centerX) {
        this.originX = centerX - 0.5;
    }
    /**
     * Sets the center of the node
     * @param centerY the new Y center
     */
    public void setCenterY(double centerY) {
        this.originY = centerY - 0.5;
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

    /**
     * Sets the center of the object to the given coordinates
     * @param centerX the x coordinate of the center
     * @param centerY the y coordinate of the center
     */
    public void setCenter(double centerX, double centerY) {
        setOriginX(centerX - getSize() / 2d);
        setOriginY(centerY - getSize() / 2d);
    }

    public void selectNode(boolean select) {
        double centerX = getCenterX();
        double centerY = getCenterY();
        setCenter(centerX, centerY);
    }


    public double getSize() {
        return Config.WSCompoSelectedSize * relativeTo.growthAnimation.getSize() / 2 + 1;
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

    public abstract boolean hasWire();

    /**
     * Clears the node from all wires connected
     */
    public abstract void clearNode();

}
