package com.Physics;

public class Component extends ObjetCircuit {

    Sheet sheet;
    private int keyComponent; //Pas tout compris à quoi il servait...

    private String name; //Changement : utilisation d'un nom pour la méthode display

    private int inputs;
    private int outputs;

    private Boolean[] truthTable;

    private Wire[] linkWireInputs;
    private Wire[] linkWireOutputs;

    Component(String name, int inputs, int outputs, Boolean[] truthTable) {
        this.name = name;
        this.inputs = inputs;
        this.outputs = outputs;
        this.truthTable = truthTable;
        linkWireInputs = new Wire[inputs];
        linkWireOutputs = new Wire[outputs];
    }

    public int getInputs() {
        return inputs;
    }

    public int getOutputs() {
        return outputs;
    }

    public Boolean[] getTruthTable() {
        return truthTable;
    }

    public void displayLinkWiresInput() {
        for (Wire i : linkWireInputs) {
            i.display();
        }
    }

    public void displayLinkWiresOutputs() {
        for (Wire i : linkWireOutputs) {
            i.display();
        }
    }

    public void addLinkWiresInput(Wire wire, int i) { //Changement au niveau de ce qui était prévu : au lieu de mettre en argument la liste des wires en entrée on les ajoute un à un ce qui sera plus facile quand on voudra construire un circuit au fur et à mesure...
        linkWireInputs[i] = wire;
    }

    public void addLinkWiresOutput(Wire wire, int i) { //Pareil que pour la fonction addLinkWiresInput
        linkWireOutputs[i] = wire;
    }

    public Wire[] getLinkWiresInput() {
        return linkWireInputs;
    }

    public Wire[] getLinkWiresOutputs() {
        return linkWireOutputs;
    }


}
