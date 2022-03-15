//Changement : au lieu de line j'ai remplacé par wire pour collé au package graphics

//Gros changement : je vais surement passer de boolean a Boolean ce qui permetterait d'avoir des Boolean == null !
package com.Physics;

public class Wire extends ObjetCircuit {

    Sheet sheet;
    String name; //Changement : nom pour la méthode display
    private Boolean state;
    private Component start; //Problème en graphique : comment savoir qui est le start, qui est le end ?
    private Component end; //Je crois qu'il est possible de faire sans start et end mais pour linstant jai pas encore trouvé

    Wire(String name) {
        this.name = name;
    }
    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getState() {
        return state;
    }

    public void display() {
        System.out.println(name);
    }

    public void connect(Component start, Component end) {
        this.start = start;
        this.end = end;
    }

    public Boolean isNull() {
        if (state==null) {
            return true;
        } else {
            return false;
        }
    }

    public void refresh() {
        for (Wire wire : start.getLinkWiresInput()) {
            if (wire == null) {
                return;
            }
        }
        int s = 0;
        for (int i=0;i<start.getInputs()-1;i++) {
            if (start.getLinkWiresInput()[i].getState()) {
                s += 2^i;
            }
        }
        setState(start.getTruthTable()[s]);
    }
}
