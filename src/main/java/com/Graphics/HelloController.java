package com.Graphics;

import com.Graphics.Workspace.Application.SheetObject;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Graphics.GraphicsManager;
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

public class HelloController {

    public Slider InputSlider;
    public Slider OutputSlider;

    // We create 3 new components, which are the most basic ones we'll be using
    SheetObject orDoor = new SheetObject("or", Color.PINK, 2, 1);
    SheetObject noDoor = new SheetObject("not", Color.web("#772288"), 1, 1);
    SheetObject andDoor = new SheetObject("and", Color.web("#03C93C"), 2, 1);


    public void no(ActionEvent actionEvent) {
        ComponentInstance no = new ComponentInstance(noDoor, 12, 6);
        currentSheet.addObject(no);
        // Component no = new Component("no",1, 1, new Boolean[] {true,false});
    }

    public void or(ActionEvent actionEvent) {
        ComponentInstance or = new ComponentInstance(orDoor, 12, 6);
        currentSheet.addObject(or);
        Component physicOr = new Component("or",1, 1, new Boolean[] {true,false});
    }

    public void and(ActionEvent actionEvent) {
        ComponentInstance and = new ComponentInstance(andDoor, 12, 6);
        currentSheet.addObject(and);
        // Component no = new Component("no",1, 1, new Boolean[] {true,false});
    }


    public void test(ActionEvent actionEvent) {
        System.out.println(InputSlider.getValue());
    }

    public void inputs(MouseEvent mouseEvent) {
        for (int i=0;i<InputSlider.getValue();i++) {
            currentSheet.ioComponent.addStartNode(currentSheet);
        }
    }

    public void outputs(MouseEvent mouseEvent) {
        for (int i=0;i<OutputSlider.getValue();i++) {
            currentSheet.ioComponent.addEndNode(currentSheet);
        }
    }
}