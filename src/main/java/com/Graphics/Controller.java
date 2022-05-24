package com.Graphics;

import com.Graphics.Workspace.Application.SheetObject;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Physics.Component;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;

import static com.Graphics.GraphicsManager.currentSheet;
import static com.Graphics.GraphicsManager.sheet;

public class Controller {

    public Slider InputSlider;
    public Slider OutputSlider;

    // We create 3 new components, which are the most basic ones we'll be using
    SheetObject orDoor = new SheetObject(0, "or", Color.PINK, 2, 1);
    SheetObject noDoor = new SheetObject(1, "not", Color.web("#772288"), 1, 1);
    SheetObject andDoor = new SheetObject(2, "and", Color.web("#03C93C"), 2, 1);


    public void no(ActionEvent actionEvent) {
        Component physicNo = new Component("no",1, 1, new Boolean[] {true,false});
        sheet.addComponent(physicNo);
        ComponentInstance no = new ComponentInstance(noDoor, 12, 6, physicNo);
        currentSheet.addObject(no);
    }

    public void or(ActionEvent actionEvent) {
        Component physicOr = new Component("or",1, 1, new Boolean[]{false, true, true, true});
        sheet.addComponent(physicOr);
        ComponentInstance or = new ComponentInstance(orDoor, 12, 6, physicOr);
        currentSheet.addObject(or);
    }

    public void and(ActionEvent actionEvent) {
        Component physicAnd = new Component("and",1, 1, new Boolean[] {false, false, false, true});
        sheet.addComponent(physicAnd);
        ComponentInstance and = new ComponentInstance(andDoor, 12, 6, physicAnd);
        currentSheet.addObject(and);
    }


    public void test(ActionEvent actionEvent) {
        System.out.println(InputSlider.getValue());
    }

    public void inputs(MouseEvent mouseEvent) {
/*        for (int i=0;i<InputSlider.getValue();i++) {
            currentSheet.ioComponent.addStartNode(currentSheet);
        }*/

        // J'ai remodifié le code sinon ça marchais pas :
        // On calcule le nombre d'entrées à placer
        int toPlace = (int) InputSlider.getValue() - currentSheet.ioComponent.startNodes.size();
        // Si il faut ajouter des entrées
        if (toPlace > 0) {
            // On ajoute ce nombre d'entrées
            for (int i = 0; i < toPlace; i++) {
                currentSheet.ioComponent.addStartNode(currentSheet);
            }
        }
        // Sinon, si il faut en enlever
        else if (toPlace < 0) {
            // On enlève ce nombre d'entrées
            for (int i=0;i<-toPlace;i++) {
                currentSheet.ioComponent.delStartNode(currentSheet.ioComponent.startNodes.get(currentSheet.ioComponent.startNodes.size() - 1), currentSheet);
            }
        }
    }

    public void outputs(MouseEvent mouseEvent) {
/*        for (int i=0;i<OutputSlider.getValue();i++) {
            currentSheet.ioComponent.addEndNode(currentSheet);
        }*/

        // J'ai remodifié le code sinon ça marchais pas :
        // On calcule le nombre d'entrées à placer
        int toPlace = (int) OutputSlider.getValue() - currentSheet.ioComponent.endNodes.size();
        // Si il faut ajouter des entrées
        if (toPlace > 0) {
            // On ajoute ce nombre d'entrées
            for (int i = 0; i < toPlace; i++) {
                currentSheet.ioComponent.addEndNode(currentSheet);
            }
        }
        // Sinon, si il faut en enlever
        else if (toPlace < 0) {
            // On enlève ce nombre d'entrées
            for (int i=0;i<-toPlace;i++) {
                currentSheet.ioComponent.delEndNode(currentSheet.ioComponent.endNodes.get(currentSheet.ioComponent.endNodes.size() - 1), currentSheet);
            }
        }
    }

    public void simulate(ActionEvent actionEvent) {
        sheet.refresh();
    }
}