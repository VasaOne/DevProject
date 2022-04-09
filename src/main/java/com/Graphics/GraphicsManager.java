package com.Graphics;

import com.Graphics.Workspace.Application.CanvasRenderer;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Graphics.Workspace.Application.SheetObject;
import com.Graphics.Workspace.Sheet.Sheet;
import com.Graphics.Workspace.Wire.WireInstance;
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
        currentSheet.ioComponent.startNodes.get(0).addWire(wire0);
        wire0.setEnd(and.inputs[0]);
        and.inputs[0].setWire(wire0);
        currentSheet.addWire(wire0);

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
