package com.Physics;

public class Component {

    Sheet sheet;
    private int keyComponent; //Pas tout compris à quoi il servait...

    private String name; //Changement : utilisation d'un nom pour la méthode display

    private int inputs;
    private int outputs;

    private Boolean[] truthTable;

    private Wire[] WireInputs;
    private Wire[] WireOutput;

    public Component(String name, int inputs, int outputs, Boolean[] truthTable) {
        this.name = name;
        this.inputs = inputs;
        this.outputs = outputs;
        this.truthTable = truthTable;
        WireInputs = new Wire[inputs];
        WireOutput = new Wire[outputs];
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

    public void displayLinkWiresInput() { //Aide au codage, ne sert à rien
        for (Wire wire : WireInputs) {

        }
    }

    public void displayLinkWiresOutputs() { //Aide au codage, ne sert à rien
        for (Wire wire : WireOutput) {

        }
    }

    public void addWireInput(Wire wire, int i) { //Changement au niveau de ce qui était prévu : au lieu de mettre en argument la liste des wires en entrée on les ajoute un à un ce qui sera plus facile quand on voudra construire un circuit au fur et à mesure...
        WireInputs[i] = wire;
    }

    public void addWireOutput(Wire wire, int i) { //Pareil que pour la fonction addLinkWiresInput
        WireOutput[i] = wire;
    }

    public Wire[] getWiresInput() {
        return WireInputs;
    }

    public Wire[] getWiresOutput() {
        return WireOutput;
    }


}
