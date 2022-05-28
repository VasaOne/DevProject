//Changement : au lieu de line j'ai remplacé par wire pour collé au package graphics

//Gros changement : je vais surement passer de boolean a Boolean ce qui permetterait d'avoir des Boolean == null !
package com.Physics;

import javax.xml.stream.events.StartDocument;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class Wire {

    private Boolean state;
    private List<Component> components;

    public Wire() {
        components = new ArrayList<>();
        state = null;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getState() {
        return state;
    }

    public void addConnection(Component component) {
        components.add(component);
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
                    System.out.println("Coucou");
                }
            }
            if (s==0) {
                System.out.println("Test");
                startComponent = component;
            }
        }
        if (startComponent.getWiresInput()[0].getState() == null) {
            return;
        } else {
            int s = 0;
            for (int i=0;i<startComponent.getInputs();i++) {
                if (startComponent.getWiresInput()[i].getState()) {
                    s += Math.pow(2, startComponent.getInputs() - i - 1);
                }

            }
            System.out.println(startComponent.getTruthTable()[s]);
            setState(startComponent.getTruthTable()[s]);
        }


    }
}
