//Changement : au lieu de line j'ai remplacé par wire pour collé au package graphics

//Gros changement : je vais surement passer de boolean a Boolean ce qui permetterait d'avoir des Boolean == null !
package com.Physics;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class Wire {

    private Boolean state;
    private Component[] componentsAttached;
    private int[] id;

    public Wire() {
        componentsAttached = new Component[2];
        state = null;
        id = new int[2];
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getState() {
        return state;
    }

    public void addConnection(Component component, int i) {
        componentsAttached[i] = component;
    }

    public Boolean isNull() {
        if (state==null) {
            return true;
        } else {
            return false;
        }
    }

    public int getId(int i) {
        return id[i];
    }

    public void setId(int id, int i) {
        this.id[i] = id;
    }

    public void refresh() {
        Component startComponent = null;
        for (Component component : componentsAttached) {
            if(component != null) {
                int s = 0;
                for (Wire wire : component.getWiresInput()) {
                    if (wire == this) {
                        s ++;
                        System.out.println("Coucou");
                    }
                }
                if (s==0) {
                    System.out.println("Test");
                    startComponent = component;
                }
            }
        }
        if (startComponent.canBeRefresh()) {
            int s = 0;
            for (int i=0;i<startComponent.getInputs();i++) {
                if (startComponent.getWiresInput()[i].getState()) {
                    System.out.println(i);
                    System.out.println(s);
                    s += Math.pow(2, startComponent.getInputs() - i - 1);
                }

            }
            this.setState(startComponent.getTruthTable()[s][this.getId(0)]);
            System.out.println(startComponent.getTruthTable()[s][0]);
        }


    }
}
