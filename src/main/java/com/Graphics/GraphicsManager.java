package com.Graphics;

import com.Application.FileManger.SaveLoadSheet;
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
        // Load the FXML file
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("interface.fxml")));
        // Create the scene based on the loaded FXML file
        Scene scene = new Scene(root);

        // Gets the workspace we'll be using
        ScrollPane pane = (ScrollPane) scene.lookup("#workspace");
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);

        // Create a new sheet
        Sheet currentSheet = new Sheet(30, 20);
        // Create a new canvas renderer which will render the sheet
        CanvasRenderer renderer = new CanvasRenderer(currentSheet, 50);
        // Sets the workspace for the renderer
        renderer.setCanvasParent(pane);

        // The main sheet contains a special component which is the sheet itself, on which the global nodes are placed
        // Then we create 3 inputs
        currentSheet.ioComponent.addStartNode(currentSheet);
        currentSheet.ioComponent.addStartNode(currentSheet);
        currentSheet.ioComponent.addStartNode(currentSheet);

        // And 2 outputs
        currentSheet.ioComponent.addEndNode(currentSheet);
        currentSheet.ioComponent.addEndNode(currentSheet);

        // We create 3 new components, which are the most basic ones we'll be using
        SheetObject orDoor = new SheetObject("or", Color.PINK, 2, 1, new Boolean[]{false, true, true, true});
        SheetObject notDoor = new SheetObject("not", Color.web("#772288"), 1, 1, new Boolean[] {true,false});
        SheetObject andDoor = new SheetObject("and", Color.web("#03C93C"), 2, 1, new Boolean[]{false, false, false, true});

        //SheetObject test = new SheetObject("test", Color.PURPLE, 7, 8, new Boolean[]{true});

        // We create instances of these components
        ComponentInstance or = new ComponentInstance(orDoor, 12, 6);
        ComponentInstance not = new ComponentInstance(notDoor, 18, 8);
        ComponentInstance and = new ComponentInstance(andDoor, 5, 3);
        //ComponentInstance testInstance = new ComponentInstance(test, 18, 8);

        // And we place them on the sheet
        currentSheet.addObject(or);
        currentSheet.addObject(not);
        currentSheet.addObject(and);

        //currentSheet.addObject(testInstance);

        // We create a new wire between the first input and the add door
        // Creates a new wire
        WireInstance wire0 = new WireInstance();
        // Connects the wire to the input
        wire0.setStart(currentSheet.ioComponent.startNodes.get(0));
        // Connects the wire to the output
        wire0.setEnd(and.inputs[0]);
        // Adds the wire to the sheet
        currentSheet.addWire(wire0);

        SaveLoadSheet.saveSheet(1, "a", Color.PINK, currentSheet);

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
