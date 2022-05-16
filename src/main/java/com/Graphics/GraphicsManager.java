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

    // Create a new sheet
    static Sheet currentSheet = new Sheet(30, 20);

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



        // Create a new canvas renderer which will render the sheet
        CanvasRenderer renderer = new CanvasRenderer(currentSheet, 50);
        // Sets the workspace for the renderer
        renderer.setCanvasParent(pane);

        // The main sheet contains a special component which is the sheet itself, on which the global nodes are placed
        // Then we create 3 inputs
        // And 2 outputs


        SheetObject orDoor = new SheetObject("or", Color.PINK, 2, 1, new Boolean[]{false, true, true, true});
        ComponentInstance no = new ComponentInstance(orDoor, 12, 6);
        currentSheet.addObject(no);


/*

        // We create a new wire between the first input and the add door
        // Creates a new wire
        WireInstance wire0 = new WireInstance();
        // Connects the wire to the input
        wire0.setStart(currentSheet.ioComponent.startNodes.get(0));
        // Connects the wire to the output
        wire0.setEnd(and.inputs[0]);
        // Adds the wire to the sheet
        currentSheet.addWire(wire0);


 */
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
