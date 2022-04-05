package com.Graphics.Workspace;

import com.Physics.Component;

/**
 * This class represents an instance of a sheet object which will be rendered on the canvas
 */
public abstract class ObjectInstance {
    public SheetObject instanceOf;

    boolean canBePlaced = true;

    private double originX;
    private double originY;

    public double getOriginX() {
        return originX;
    }
    public double getOriginY() {
        return originY;
    }

    public double getCenterX() { return originX + getWidth() / 2d; }
    public double getCenterY() { return originY + getHeight() / 2d; }

    public double getWidth() {
        return instanceOf.getWidth();
    }
    public double getHeight() {
        return instanceOf.getHeight();
    }

    /**
     * Sets the origin of the object to the given coordinates
     * @param x the x coordinate of the origin
     */
    public void setOriginX(double x) {
        originX = x;
    }
    /**
     * Sets the origin of the object to the given coordinates
     * @param y the y coordinate of the origin
     */
    public void setOriginY(double y) {
        originY = y;
    }

    /**
     * Sets the origin of the object to the given coordinates
     * @param x the x coordinate of the origin
     * @param y the y coordinate of the origin
     */
    public void setOrigin(double x, double y) {
        originX = x;
        originY = y;
    }

    /**
     * Sets the center of the object to the given coordinates
     * @param centerX the x coordinate of the center
     */
    public void setCenterX(double centerX) { originX = centerX - getWidth() / 2d; }
    /**
     * Sets the center of the object to the given coordinates
     * @param centerY the y coordinate of the center
     */
    public void setCenterY(double centerY) { originY = centerY - getHeight() / 2d; }

    /**
     * Sets the center of the object to the given coordinates
     * @param centerX the x coordinate of the center
     * @param centerY the y coordinate of the center
     */
    public void setCenter(double centerX, double centerY) {
        setOrigin(centerX - getWidth() / 2d, centerY - getHeight() / 2d);
    }

    protected Component physicComponent;
    public Component getPhysicComponent() {
        return physicComponent;
    }

    public boolean isSelected() {
        return false;
    }
}
