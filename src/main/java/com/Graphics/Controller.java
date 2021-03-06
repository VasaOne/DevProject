package com.Graphics;

import com.Application.FileManger.ComponentData;
import com.Application.FileManger.ComponentNotFoundException;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Physics.Component;
import com.Physics.Wire;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


import java.io.IOException;
import java.util.ArrayList;

import com.Application.FileManger.SaveLoadSheet;

import static com.Graphics.GraphicsManager.*;

public class Controller {

    public Slider InputSlider;
    public Slider OutputSlider;

    public Slider ScaleSlider;

    public MenuButton ComponentList;

    public Button Transform;
    public Button Simulate;

    public TextField NameInput;
    public ColorPicker Picker;

    public void getAvailableComponents(MouseEvent mouseEvent) {
        ComponentList.getItems().clear();
        ArrayList<MenuItem> items = new ArrayList<>();
        System.out.println("heyyy"+SaveLoadSheet.componentData[0]);
        for (ComponentData data : SaveLoadSheet.componentData) {
            MenuItem item = new MenuItem(data.name);
            item.setOnAction(event -> addDoor(data.id));
            items.add(item);
        }
        ComponentList.getItems().addAll(items);
    }

    public void addDoor(int id) {
        ComponentData data = SaveLoadSheet.componentData[id];
        Component physicComponent = new Component(data.name, data.inputs, data.outputs, SaveLoadSheet.truthTables[id]);
        physicSheet.addComponent(physicComponent);
        ComponentInstance instance = new ComponentInstance(SaveLoadSheet.loadedObjects[id], data.inputs, data.outputs, physicComponent);
        currentSheet.addObject(instance);
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
        for (Wire wire : physicSheet.getWires()) {
            wire.setState(null);
        }
        physicSheet.refresh();
        for (Wire wire : physicSheet.getWires()) {
            System.out.println(wire.getState());
        }
        currentSheet.refresh();
    }

    public void setSimulateState(boolean state) {
        Transform.setDisable(!state);
    }

    public void transformSheet() {
        SaveLoadSheet.saveSheet(SaveLoadSheet.loadedObjects.length, NameInput.getText(), Picker.getValue(), currentSheet);
    }

    public void loadLastSheet(MouseEvent event) throws ComponentNotFoundException, IOException {
        SaveLoadSheet.loadAll();
    }
}