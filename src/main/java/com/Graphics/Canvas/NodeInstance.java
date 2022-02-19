package com.Graphics.Canvas;

public class NodeInstance extends ObjectInstance {
    public static SheetObject defaultNode;

    public ComponentInstance relativeTo;

    @Override
    public double getOriginX() {
        return relativeTo.getOriginX() + originX;
    }
    @Override
    public double getOriginY() {
        return relativeTo.getOriginY() + originY;
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
