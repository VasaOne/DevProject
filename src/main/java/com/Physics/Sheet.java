package com.Physics;

import java.util.*;

public class Sheet {

    private List<Component> components;
    private List<Wire> wires;
    private List<Wire> inputs;
    private List<OutputNode> outputs;

    public Sheet() {
        wires = new ArrayList<>();
        components = new ArrayList<>();
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
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

    public void addWireInput(Wire wire) {
        inputs.add(wire);
    }

    public List<Wire> getWireInputs() {
        return inputs;
    }

    public void addOutput() {
        OutputNode node = new OutputNode(false, outputs.size());
        outputs.add(node);
    }

    public void refresh() {
        boolean finish = true;

        do {
            System.out.println("hey");
            List<Wire> nullWires = new ArrayList<>();
            finish = false;
            for (Wire wire : wires) {
                if (wire.isNull()) {
                    finish = true;
                    nullWires.add(wire);
                    System.out.println("addwire");
                }
            }
            for (Wire wire : nullWires) {
                wire.refresh();
            }
        } while(finish); //On actualise tant qu'il reste des wires sans état (cad null)
    }
}
