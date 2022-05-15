//Changement : au lieu de line j'ai remplacé par wire pour collé au package graphics

//Gros changement : je vais surement passer de boolean a Boolean ce qui permetterait d'avoir des Boolean == null !
package com.Physics;

import javax.xml.stream.events.StartDocument;
import java.lang.Math;

public class Wire {

    Sheet sheet;
    private Boolean state;
    private Component[] components;

    public Wire() {}
    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getState() {
        return state;
    }

    public void connect(Component[] components) {
        this.components = components;
    }

    public Boolean isNull() {
        if (state==null) {
            return true;
        } else {
            return false;
        }
    }

    public void refresh() {
        Component startComponent = null;
        for (Component component : components) {
            int s = 0;
            for (Wire wire : component.getWiresInput()) {
                if (wire == this) {
                    s ++;
                }
            }
            if (s==0) {
                startComponent = component;
            }
        }
        if (startComponent == null) {
            return;
        }
        int s = 0;
        for (int i=0;i<startComponent.getInputs();i++) {
            if (startComponent.getWiresInput()[i].getState()) {
                s += Math.pow(2,startComponent.getInputs()-i-1);
            }
        }
        //System.out.println(start.getTruthTable()[s]);
        //System.out.println(name);
        setState(startComponent.getTruthTable()[s]);
    }
}
