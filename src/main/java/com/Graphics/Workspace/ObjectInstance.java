package com.Graphics.Workspace;

import com.Physics.Component;

/**
 * This class represents an instance of a sheet object which will be rendered on the canvas
 */
public abstract class ObjectInstance {
    public SheetObject instanceOf;

    protected double originX;
    protected double originY;

    public double getOriginX() {
        return originX;
    }
    public double getOriginY() {
        return originY;
    }

    public double getCenterX() { return originX + instanceOf.getWidth() / 2d; }
    public double getCenterY() { return originY + instanceOf.getHeight() / 2d; }

    public void setOriginX(double centerX) { originX = centerX - instanceOf.getWidth() / 2d; }
    public void setOriginY(double centerY) { originY = centerY - instanceOf.getHeight() / 2d; }

    protected Component physicComponent;
    public Component getPhysicComponent() {
        return physicComponent;
    }
}
