package com.Graphics.Workspace.Node;

import com.Graphics.Workspace.Component.ObjectInstance;
import com.Graphics.Workspace.Wire.WireInstance;

import java.util.Objects;

public class InputNode extends GraphicNode {
    boolean isInput = true;

    final WireInstance abstractWire = new WireInstance(null, this, false);
    public WireInstance wireConnected;

    public InputNode(ObjectInstance relativeTo, double originX, double originY, int id) {
        super(relativeTo, originX, originY, id);
        wireConnected = abstractWire;
    }

    /**
     * Returns the state of the wire connected to this node
     * @return the state of the node
     */
    @Override
    public boolean getState() {
        return wireConnected.getState();
    }
    /**
     * Sets its state by setting the state of the connected wire
     * @param state the state to set
     */
    @Override
    public void setState(boolean state) {
        wireConnected.setState(state);
    }

    /**
     * Called by wire itself to set itself as the connected wire
     * @param wire the wire to connect to
     */
    public void setWire(WireInstance wire) {
        wireConnected = wire;
    }

    /**
     * Called by the wire to remove itself from the connected wire
     */
    public void removeWire() {
        wireConnected = abstractWire;
    }

    public boolean hasWire() {
        return !Objects.equals(abstractWire, wireConnected);
    }

    @Override
    public void clearNode() {
        wireConnected.remove();
    }
}
