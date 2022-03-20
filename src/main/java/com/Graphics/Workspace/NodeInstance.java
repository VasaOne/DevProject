package com.Graphics.Workspace;

public class NodeInstance extends ObjectInstance {
    public static SheetObject defaultNode;

    ComponentInstance relativeTo;

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


    private boolean state;
    public boolean getState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }

    public NodeInstance(ComponentInstance relativeTo, double centerX, double centerY) {
        this.relativeTo = relativeTo;
        instanceOf = defaultNode;
        this.setOriginX(centerX);
        this.setOriginY(centerY);
        state = false;
    }

    private WireInstance wire;
    public void setWire(WireInstance wire) {
        this.wire = wire;
    }
    public WireInstance removeWire() {
        WireInstance wire = this.wire;
        this.wire = null;
        return wire;
    }
    public WireInstance getWire() {
        return wire;
    }
}