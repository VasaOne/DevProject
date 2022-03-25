package com.Graphics;

import com.Graphics.Workspace.*;
import com.Physics.Component;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GraphicsManager extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("interface.fxml")));
        Scene scene = new Scene(root);

        ScrollPane pane = (ScrollPane) scene.lookup("#workspace");
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);

        Sheet currentSheet = new Sheet(30, 30);
        CanvasRenderer renderer = new CanvasRenderer(currentSheet, 50);
        renderer.setCanvasParent(pane);

        SheetObject orDoor = new SheetObject("or", Color.web("#33CC33"), 2, 1, new Boolean[]{false, true, true, true});
        SheetObject notDoor = new SheetObject("not", Color.web("#772288"), 1, 1, new Boolean[] {true,false});
        SheetObject andDoor = new SheetObject("and", Color.web("#03C93C"), 2, 1, new Boolean[]{false, false, false, true});
        SheetObject button = new SheetObject("b", Color.web("#7922A8"), 0, 1, new Boolean[] {false,true});
        SheetObject led = new SheetObject("l", Color.web("#7922A8"), 1, 0);
        //SheetObject sevenSgtDisp = new SheetObject("7 segment", Color.web("#00FFCC"), 4, 7);
        //SheetObject fourBitsAdder = new SheetObject("4 bit adder", Color.PURPLE, 9, 5);

        NodeInstance.defaultNode = SheetObject.DefaultNode();

        ComponentInstance or = new ComponentInstance(orDoor, 12, 6);
        ComponentInstance not = new ComponentInstance(notDoor, 18, 8);
        ComponentInstance and = new ComponentInstance(andDoor, 5, 3);
        ComponentInstance button1 = new ComponentInstance(button, 0, 3);
        ComponentInstance button2 = new ComponentInstance(button, 0, 6);
        ComponentInstance button3 = new ComponentInstance(button, 0, 9);
        ComponentInstance led1 = new ComponentInstance(led, 25, 8);
        //ComponentInstance fourBit = new ComponentInstance(fourBitsAdder, 20, 5);

        currentSheet.addObject(or);
        currentSheet.addObject(not);
        currentSheet.addObject(and);
        currentSheet.addObject(button1);
        currentSheet.addObject(button2);
        currentSheet.addObject(button3);
        currentSheet.addObject(led1);
        //currentSheet.addObject(new ComponentInstance(sevenSgtDisp, 17, 3));

        WireInstance wire0 = new WireInstance();
        wire0.setStart(button1.outputs[0]);
        wire0.setEnd(and.inputs[0]);
        currentSheet.addWire(wire0);

        WireInstance wire1 = new WireInstance();
        wire1.setStart(button2.outputs[0]);
        wire1.setEnd(and.inputs[1]);
        currentSheet.addWire(wire1);

        WireInstance wire2 = new WireInstance();
        wire2.setStart(and.outputs[0]);
        wire2.setEnd(or.inputs[0]);
        currentSheet.addWire(wire2);

        WireInstance wire3 = new WireInstance();
        wire3.setStart(button3.outputs[0]);
        wire3.setEnd(or.inputs[1]);
        currentSheet.addWire(wire3);

        WireInstance wire4 = new WireInstance();
        wire4.setStart(or.outputs[0]);
        wire4.setEnd(not.inputs[0]);
        currentSheet.addWire(wire4);

        WireInstance wire5 = new WireInstance();
        wire5.setStart(not.outputs[0]);
        wire5.setEnd(led1.inputs[0]);
        currentSheet.addWire(wire5);


        AnimationTimer animate = new AnimationTimer() {
            @Override
            public void handle(long l) {
                CanvasAnimator(renderer);
            }
        };
        animate.start();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void CanvasAnimator(CanvasRenderer renderer) {
        renderer.renderGraphicContext();
    }
}
