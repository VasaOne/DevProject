package com.Graphics.Workspace;

public class NodeInstance extends ObjectInstance {
    public static SheetObject defaultNode;

    ObjectInstance relativeTo;
    boolean isInput;
    boolean isGlobalInput = false;

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

    public NodeInstance(ObjectInstance relativeTo, boolean isInput, double centerX, double centerY) {
        this.relativeTo = relativeTo;
        this.isInput = isInput;
        instanceOf = defaultNode;
        this.setOriginX(centerX);
        this.setOriginY(centerY);
        setWire(new WireInstance(this, this, false));
        state = false;
    }
    public NodeInstance(ObjectInstance relativeTo, boolean isInput, double centerX, double centerY, boolean isGlobalInput) {
        this(relativeTo, isInput, centerX, centerY);
        instanceOf = defaultNode;
        this.isGlobalInput = isGlobalInput;
    }

    private WireInstance wire;
    public void setWire(WireInstance wire) {
        this.wire = wire;
    }
    public WireInstance removeWire() {
        WireInstance wire = this.wire;
        this.wire = new WireInstance(this, this, false);
        return wire;
    }
    public WireInstance getWire() {
        return wire;
    }
}