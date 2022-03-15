package com.Graphics;

import com.Graphics.Workspace.*;
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

        Sheet currentSheet = new Sheet(20, 30);
        CanvasRenderer renderer = new CanvasRenderer(currentSheet, 50);
        renderer.setCanvasParent(pane);

        SheetObject addDoor = new SheetObject("Add", Color.web("#33CC33"), 2, 1);
        SheetObject notDoor = new SheetObject("not", Color.web("#772288"), 1, 1);
        SheetObject sevenSgtDisp = new SheetObject("7 segment", Color.web("#00FFCC"), 4, 7);
        SheetObject fourBitsAdder = new SheetObject("4 bit adder", Color.PURPLE, 9, 5);

        NodeInstance.defaultNode = SheetObject.DefaultNode();

        ComponentInstance add = new ComponentInstance(addDoor, 2, 2);
        ComponentInstance not = new ComponentInstance(notDoor, 10, 3);
        ComponentInstance fourBit = new ComponentInstance(fourBitsAdder, 20, 5);

        currentSheet.addObject(add);
        currentSheet.addObject(not);
        currentSheet.addObject(new ComponentInstance(sevenSgtDisp, 17, 3));
        currentSheet.addObject(fourBit);

        WireInstance wire = new WireInstance();
        wire.setStart(add.outputs[0]);
        wire.setEnd(not.inputs[0]);

        WireInstance wire2 = new WireInstance();
        wire2.setStart(not.outputs[0]);
        wire2.setEnd(fourBit.inputs[0]);

        currentSheet.addWire(wire);
        currentSheet.addWire(wire2);

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
