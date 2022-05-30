package com.Graphics;

import com.Graphics.Workspace.Application.SheetObject;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Physics.Component;
import javafx.application.Application;
import com.Physics.Wire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;

import static com.Graphics.GraphicsManager.currentSheet;
import static com.Graphics.GraphicsManager.sheet;
import com.Application.FileManger.SaveLoadSheet;

public class Controller {

    public Slider InputSlider;
    public Slider OutputSlider;

    public Slider ScaleSlider;


    public Button Transform;
    public Button Simulate;

    public TextField NameInput;
    public ColorPicker Picker;

    // We create 3 new components, which are the most basic ones we'll be using
    SheetObject orDoor = new SheetObject(0, "or", Color.PINK, 2, 1);
    SheetObject noDoor = new SheetObject(1, "not", Color.web("#772288"), 1, 1);
    SheetObject andDoor = new SheetObject(2, "and", Color.web("#03C93C"), 2, 1);


    public void no(ActionEvent actionEvent) {
        Component physicNo = new Component("no",1, 1, new Boolean[][] {{true},{false}});
        sheet.addComponent(physicNo);
        ComponentInstance no = new ComponentInstance(noDoor, 12, 6, physicNo);
        currentSheet.addObject(no);
    }

    public void or(ActionEvent actionEvent) {
        Component physicOr = new Component("or",1, 1, new Boolean[][] {{false}, {true}, {true}, {true}});
        sheet.addComponent(physicOr);
        ComponentInstance or = new ComponentInstance(orDoor, 12, 6, physicOr);
        currentSheet.addObject(or);
    }

    public void and(ActionEvent actionEvent) {
        Component physicAnd = new Component("and",1, 1, new Boolean[][] {{false}, {false}, {false}, {true}});
        sheet.addComponent(physicAnd);
        ComponentInstance and = new ComponentInstance(andDoor, 12, 6, physicAnd);
        currentSheet.addObject(and);
    }


    public void test(ActionEvent actionEvent) {
        System.out.println(InputSlider.getValue());
    }

    public void inputs(MouseEvent mouseEvent) {
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
                currentSheet.ioComponent.delStartNode(currentSheet);
            }
        }
    }

    public void outputs(MouseEvent mouseEvent) {
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
                currentSheet.ioComponent.delEndNode(currentSheet);
            }
        }
    }

    public void scale(MouseEvent mouseEvent) {
        //On calcule la nouvelle échelle à appliquer
        double newScale = ScaleSlider.getValue();
        GraphicsManager.renderer.setScale(newScale);
    }

    public void simulate(ActionEvent actionEvent) {
        for (Wire wire : sheet.getWires()) {
            wire.setState(null);
        }
        sheet.refresh();
        for (Wire wire : sheet.getWires()) {
            System.out.println(wire.getState());
        }
        currentSheet.refresh();
    }

    public void setSimulateState(boolean state) {
        Simulate.setDisable(!state);
        Transform.setDisable(!state);
    }

    public void transformSheet() {
        SaveLoadSheet.saveSheet(SaveLoadSheet.loadedObjects.length, NameInput.getText(), Picker.getValue(), currentSheet);
    }
}