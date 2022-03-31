package com.Graphics.Workspace;

import java.util.Objects;

public class InputNode extends GraphicNode {
    boolean isInput = true;

    final WireInstance abstractWire = new WireInstance(null, this, false);
    public WireInstance wireConnected;

    public InputNode(ObjectInstance relativeTo, double originX, double originY) {
        super(relativeTo, originX, originY);
        wireConnected = abstractWire;
    }

    @Override
    public boolean getState() {
        return wireConnected.getState();
    }
    @Override
    public void setState(boolean state) {
        wireConnected.setState(state);
    }

    public void setWire(WireInstance wire) {
        wireConnected = wire;
    }
    public void removeWire() {
        wireConnected = abstractWire;
    }

    public boolean hasWire() {
        return !Objects.equals(abstractWire, wireConnected);
    }
}
