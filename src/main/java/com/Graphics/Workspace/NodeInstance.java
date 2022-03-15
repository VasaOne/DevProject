package com.Graphics.Workspace;

public class NodeInstance extends ObjectInstance {
    public static SheetObject defaultNode;

    private ComponentInstance relativeTo;

    /**
     * The position on the sheet relatively to the origin, in NU
     */
    @Override
    public double getOriginX() {
        return relativeTo.getOriginX() + originX;
    }
    /**
     * The position on the sheet relatively to the origin, in NU
     */
    @Override
    public double getOriginY() {
        return relativeTo.getOriginY() + originY;
    }

    /**
     * The position on the sheet relatively to the origin, in NU
     */
    @Override
    public double getCenterX() {
        return relativeTo.getOriginX() + super.getCenterX();
    }
    /**
     * The position on the sheet relatively to the origin, in NU
     */
    @Override
    public double getCenterY() {
        return relativeTo.getOriginY() + super.getCenterY();
    }


    public boolean state;

    public NodeInstance(ComponentInstance relativeTo, double centerX, double centerY) {
        this.relativeTo = relativeTo;
        instanceOf = defaultNode;
        this.setOriginX(centerX);
        this.setOriginY(centerY);
        state = false;
    }
}