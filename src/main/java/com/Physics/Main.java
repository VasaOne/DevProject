package com.Physics;

public class Main {

    Sheet sheet = new Sheet();

    public static void main(String[] args) {

        //test avec trois portes, trois entrées, et une led...
        //Image du circuit https://drive.google.com/file/d/1MXw_zg9wkNohjrMirr88znJrv8MFNUU2/view?usp=sharing

        //setup

        Component no = new Component("no",1, 1, new Boolean[] {true,false});
        Component ou = new Component("ou",2, 1, new Boolean[]{false, true, true, true});
        Component et = new Component("et",2, 1, new Boolean[] {false, false, false, true});
        Component button1 = new Component("button1",0,1,new Boolean[] {false, true});
        Component button2 = new Component("button2",0,1,new Boolean[] {false, true});
        Component button3 = new Component("button3",0,1,new Boolean[] {false, true});
        Component led = new Component("led",1,0,null);
        Wire wire1 = new Wire();
        Wire wire2 = new Wire();
        Wire wire3 = new Wire();
        Wire wire4 = new Wire();
        Wire wire5 = new Wire();
        Wire wire6 = new Wire();
        /*
        sheet.addComponent(no);
        sheet.addComponent(ou);
        sheet.addComponent(et);
        sheet.addComponent(button1);
        sheet.addComponent(button2);
        sheet.addComponent(button3);
        sheet.addComponent(led);
        sheet.addWire(wire1);
        sheet.addWire(wire2);
        sheet.addWire(wire3);
        sheet.addWire(wire4);
        sheet.addWire(wire5);
        sheet.addWire(wire6);
        */

        //connexions
        //wire1
        wire1.connect(new Component[] {et,button1});
        button1.addWireOutput(wire1, 0);
        et.addWireInput(wire1, 0);
        //wire2
        wire2.connect(new Component[] {button2,et});
        button2.addWireOutput(wire2, 0);
        et.addWireInput(wire2, 1);
        //wire3
        wire3.connect(new Component[] {et,ou});
        et.addWireOutput(wire3,0);
        ou.addWireInput(wire3,0);
        //wire4
        wire4.connect(new Component[] {button3, ou});
        button3.addWireOutput(wire4,0);
        ou.addWireInput(wire4,1);
        //wire5
        wire5.connect(new Component[] {ou,no});
        ou.addWireOutput(wire5,0);
        no.addWireInput(wire5,0);
        //wire6
        wire6.connect(new Component[] {no,led});
        no.addWireOutput(wire6,0);
        led.addWireInput(wire6,0);

        //Etats des button
        //(deux choix : soit on initilise les wires qui sortent d button, soit on suppose que les button ont e,
        //fait une entrée que l'utilisateur ne verra pas, et on initialise les buttons grâce à ces wires
        //A voir le plus simple, ici j'utilise la première solution
        wire1.setState(true);
        wire2.setState(false);
        wire4.setState(true);

        //sheet.refresh();

        System.out.println("wire3");
        System.out.println(wire3.getState());

        System.out.println("wire5");
        System.out.println(wire5.getState());

        System.out.println("wire6");
        System.out.println(wire6.getState());

    }
}
