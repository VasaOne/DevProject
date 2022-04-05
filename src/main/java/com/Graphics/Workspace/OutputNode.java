package com.Graphics.Workspace;

import java.util.ArrayList;

public class OutputNode extends GraphicNode {
    final WireInstance abstractWire = new WireInstance(this, null, false);
    public ArrayList<WireInstance> wiresConnected = new ArrayList<>();

    public OutputNode(ObjectInstance relativeTo, double originX, double originY) {
        super(relativeTo, originX, originY);
        wiresConnected.add(abstractWire);
    }

    //Pour plus d'efficacité, on considère que tous les fils ont la même valeur :
    /**
     * Returns the state of the first wire connected to this node
     */
    @Override
    public boolean getState() {
        return wiresConnected.get(0).getState();
    }

    /**
     * Sets the state of all wires connected to this node
     * @param state the state to set
     */
    @Override
    public void setState(boolean state) {
        for (WireInstance wire: wiresConnected) {
            wire.setState(state);
        }
    }

    /**
     * Gets the position of the origin of the node
     * @return the x coordinate of the node
     */
    @Override
    public double getOriginX() {
        return relativeTo.getOriginX() + relativeTo.getWidth() + originX;
    }
    /**
     * Gets the position of the origin of the node
     * @return the y coordinate of the node
     */
    @Override
    public double getOriginY() {
        return relativeTo.getOriginY() + originY;
    }

    /**
     * Called by wire itself to connect to this node
     * @param wire the wire to connect
     */
    public void addWire(WireInstance wire) {
        wiresConnected.add(wire);
    }

    /**
     * Called by wire itself to disconnect from this node
     * @param wire the wire to disconnect
     */
    public void removeWire(WireInstance wire) {
        wiresConnected.remove(wire);
    }
}
