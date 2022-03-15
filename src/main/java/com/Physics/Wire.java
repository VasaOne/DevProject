//Changement : au lieu de line j'ai remplacé par wire pour collé au package graphics

//Gros changement : je vais surement passer de boolean a Boolean ce qui permetterait d'avoir des Boolean == null !
package com.Physics;

import java.lang.Math;

public class Wire extends ObjetCircuit {

    String name; //Changement : nom pour la méthode display

    private Boolean state;

    //Il n'y a plus de début et de fin mais un fil ne peut que relier que deux composants
    //prochaine amélioration : avoir plus de deux composants pour un seul fil
    private Component component1;
    private Component component2;

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

    public void connect(Component component1, Component component2) {
        this.component1 = component1;
        this.component2 = component2;
    }

    public Boolean isNull() {
        if (state==null) {
            return true;
        } else {
            return false;
        }
    }

    public void refresh() {
        for (Wire wire : component1.getWiresInput()) {
            if (wire == null) {
                break;
            }
            int s = 0;
            for (int i=0;i<component1.getInputs();i++) {
                if (component1.getWiresInput()[i].getState()) {
                    s += Math.pow(2, component1.getInputs() - i - 1);
                }
            }
            setState(component1.getTruthTable()[s]);
            return;
        }
        for (Wire wire : component2.getWiresInput()) {
            if (wire == null) {
                break;
            }
            int s = 0;
            for (int i=0;i<component2.getInputs();i++) {
                if (component2.getWiresInput()[i].getState()) {
                    s += Math.pow(2,component2.getInputs()-i-1);
                }
            }
            setState(component2.getTruthTable()[s]);
            return;
        }
        //System.out.println(start.getTruthTable()[s]);
        //System.out.println(name);

    }
}
