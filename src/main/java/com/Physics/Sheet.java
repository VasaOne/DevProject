package com.Physics;

import java.util.*;

public class Sheet extends ObjetCircuit {

    private List<Component> components;
    private List<Wire> wires;

    public Sheet() {
        wires = new ArrayList<>();
        components = new ArrayList<>();
    }

    public List<Component> getComponents() {
        return components;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public List<Wire> getWires() {
        return wires;
    }

    public void addWire(Wire wire) {
        wires.add(wire);
    }

    public void refresh() {
        boolean finish = true;
        do {
            List<Wire> nullWires = new ArrayList<>();
            finish = false;
            for (Wire wire : wires) {
                if (wire.isNull()) {
                    finish = true;
                    nullWires.add(wire);
                }
            }
            for (Wire wire : nullWires) {
                wire.refresh();
            }
        } while(finish); //On actualise tant qu'il reste des wires sans état (cad null)
    }
}
