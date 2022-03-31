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

        Sheet currentSheet = new Sheet(30, 20);
        CanvasRenderer renderer = new CanvasRenderer(currentSheet, 50);
        renderer.setCanvasParent(pane);

        currentSheet.ioComponent.addStartNode(currentSheet);
        currentSheet.ioComponent.addStartNode(currentSheet);
        currentSheet.ioComponent.addStartNode(currentSheet);

        currentSheet.ioComponent.addEndNode(currentSheet);
        currentSheet.ioComponent.addEndNode(currentSheet);


        SheetObject orDoor = new SheetObject("or", Color.web("#33CC33"), 2, 1, new Boolean[]{false, true, true, true});
        SheetObject notDoor = new SheetObject("not", Color.web("#772288"), 1, 1, new Boolean[] {true,false});
        SheetObject andDoor = new SheetObject("and", Color.web("#03C93C"), 2, 1, new Boolean[]{false, false, false, true});

        ComponentInstance or = new ComponentInstance(orDoor, 12, 6);
        ComponentInstance not = new ComponentInstance(notDoor, 18, 8);
        ComponentInstance and = new ComponentInstance(andDoor, 5, 3);

        currentSheet.addObject(or);
        currentSheet.addObject(not);
        currentSheet.addObject(and);

        WireInstance wire0 = new WireInstance();
        wire0.setStart(currentSheet.ioComponent.startNodes.get(0));
        wire0.setEnd(and.inputs[0]);
        currentSheet.addWire(wire0);

//        WireInstance wire1 = new WireInstance();
//        wire1.setStart(currentSheet.input.nodes.get(1));
//        wire1.setEnd(and.inputs[1]);
//        currentSheet.addWire(wire1);
//
//        WireInstance wire2 = new WireInstance();
//        wire2.setStart(and.outputs[0]);
//        wire2.setEnd(or.inputs[0]);
//        currentSheet.addWire(wire2);
//
//        WireInstance wire3 = new WireInstance();
//        wire3.setStart(currentSheet.input.nodes.get(2));
//        wire3.setEnd(or.inputs[1]);
//        currentSheet.addWire(wire3);
//
//        WireInstance wire4 = new WireInstance();
//        wire4.setStart(or.outputs[0]);
//        wire4.setEnd(not.inputs[0]);
//        currentSheet.addWire(wire4);
//
//        WireInstance wire5 = new WireInstance();
//        wire5.setStart(not.outputs[0]);
//        wire5.setEnd(currentSheet.output.nodes.get(1));
//        currentSheet.addWire(wire5);


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
