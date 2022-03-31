package com.Graphics.Workspace;

import java.util.ArrayList;
import java.util.Objects;

public class OutputNode extends GraphicNode {
    boolean isInput = false;

    final WireInstance abstractWire = new WireInstance(this, null, false);
    public ArrayList<WireInstance> wiresConnected = new ArrayList<>();

    public OutputNode(ObjectInstance relativeTo, double originX, double originY) {
        super(relativeTo, originX, originY);
        wiresConnected.add(abstractWire);
    }

    @Override
/*    public boolean getState() {
        for (WireInstance wire: wiresConnected) {
            if (wire.getState()) {
                return true;
            }
        }
        return false;
    }
*/
    //Pour plus d'efficacité, on considère que tous les fils ont la même valeur :
    public boolean getState() {
        return wiresConnected.get(0).getState();
    }

    @Override
    public void setState(boolean state) {
        for (WireInstance wire: wiresConnected) {
            wire.setState(state);
        }
    }

    public void addWire(WireInstance wire) {
        wiresConnected.add(wire);
    }
    public boolean removeWire(WireInstance wire) {
        return wiresConnected.remove(wire);
    }
}
