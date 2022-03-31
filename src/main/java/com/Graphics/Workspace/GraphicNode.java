package com.Graphics.Workspace;

import com.Config;
import javafx.scene.canvas.GraphicsContext;

import java.util.Objects;

public abstract class GraphicNode {
    ObjectInstance relativeTo;
    boolean isGlobal;

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
     * The position on the sheet relative to the origin, in NU
     */
    public double getCenterX() {
        return getOriginX() + 0.5;
    }
    /**
     * The position on the sheet relative to the origin, in NU
     */
    public double getCenterY() {
        return getOriginY() + 0.5;
    }

    public void drawNode(GraphicsContext context, double scale) {
        if (getState()) {
            context.setFill(Config.WSOnNodesColor);
        }
        else {
            context.setFill(Config.WSOffNodesColor);
        }
        context.fillOval(getOriginX() * scale, getOriginY() * scale, scale, scale);
    }
}
