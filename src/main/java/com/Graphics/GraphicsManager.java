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

    public static Sheet currentSheet;

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

        /*// Create a new sheet
        Sheet currentSheet = new Sheet(30, 20);
        // Create a new canvas renderer which will render the sheet
        CanvasRenderer renderer = new CanvasRenderer(currentSheet, 50);
        // Sets the workspace for the renderer
        renderer.setCanvasParent(pane);*/

        /*// The main sheet contains a special component which is the sheet itself, on which the global nodes are placed
        // Then we create 3 inputs
        currentSheet.ioComponent.addStartNode(currentSheet);
        currentSheet.ioComponent.addStartNode(currentSheet);
        currentSheet.ioComponent.addStartNode(currentSheet);

        // And 2 outputs
        currentSheet.ioComponent.addEndNode(currentSheet);
        currentSheet.ioComponent.addEndNode(currentSheet);*/

        // We create 3 new components, which are the most basic ones we'll be using
        SheetObject orDoor = new SheetObject(0, "or", Color.PINK, 2, 1, new Boolean[]{false, true, true, true});
        SheetObject notDoor = new SheetObject(1, "not", Color.web("#772288"), 1, 1, new Boolean[] {true,false});
        SheetObject andDoor = new SheetObject(2, "and", Color.web("#03C93C"), 2, 1, new Boolean[]{false, false, false, true});

        //SheetObject test = new SheetObject("test", Color.PURPLE, 7, 8, new Boolean[]{true});

        /*// We create instances of these components
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
        currentSheet.addWire(wire0);*/
        SaveLoadSheet.loadedObjects.add(orDoor);
        SaveLoadSheet.loadedObjects.add(notDoor);
        SaveLoadSheet.loadedObjects.add(andDoor);

        //SaveLoadSheet.saveSheet(1, "a", Color.PINK, currentSheet);

        String fileContent = "id: 1\n" +
                "name: a\n" +
                "color: 0xffc0cbff\n" +
                "inputs: 3\n" +
                "outputs: 2\n" +
                "width: 30.0\n" +
                "height: 20.0\n" +
                "components: 0, 1, 2\n" +
                "componentsX: 14.1, 20.4, 7.4\n" +
                "componentsY: 7.7, 9.0, 4.7\n" +
                "wiresStartComp: -1, 2\n" +
                "wiresStartNode: 0, 0\n" +
                "wiresEndComp: 2, 1\n" +
                "wiresEndNode: 0, 0";

        currentSheet = SaveLoadSheet.loadSheet(fileContent);
        // Create a new canvas renderer which will render the sheet
        CanvasRenderer renderer = new CanvasRenderer(currentSheet, 50);
        // Sets the workspace for the renderer
        renderer.setCanvasParent(pane);


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
