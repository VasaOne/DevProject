package com.Graphics;

import com.Application.FileManger.ComponentNotFoundException;
import com.Application.FileManger.SaveLoadSheet;
import com.Graphics.Workspace.Application.CanvasRenderer;
import com.Graphics.Workspace.Sheet.Sheet;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class GraphicsManager extends Application {

    public static Sheet currentSheet;
    public static com.Physics.Sheet physicSheet;
    public static com.Graphics.Workspace.Application.CanvasRenderer renderer;

    @FXML
    public static Controller controller;

    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("interface.fxml")));
        // Set root of fxml file
        Parent root = loader.load();
        // Create the scene based on the loaded FXML file
        Scene scene = new Scene(root);
        // Get the controller of the scene to access elements
        controller = loader.getController();
        controller.setSimulateState(false);

        // Gets the workspace we'll be using
        ScrollPane pane = (ScrollPane) scene.lookup("#workspace");
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);

        String fileContent = "id: 3\n" +
                "name: a\n" +
                "color: 0xffc0cbff\n" +
                "inputs: 2\n" +
                "outputs: 1\n" +
                "width: 30.0\n" +
                "height: 20.0\n" +
                "components: 0\n" +
                "componentsX: 15\n" +
                "componentsY: 4.7\n" +
                "wiresStartComp: -1\n" +
                "wiresStartNode: 0\n" +
                "wiresEndComp: 0\n" +
                "wiresEndNode: 0";


//        try {
//            SaveLoadSheet.loadAll();
//            //SaveLoadSheet.loadSheet(fileContent);
//        }
//        catch (ComponentNotFoundException e) {
//            System.err.println("File error");
//        }
        SaveLoadSheet.createNewSheet();

        controller.InputSlider.setValue(currentSheet.ioComponent.startNodes.size());
        controller.OutputSlider.setValue(currentSheet.ioComponent.endNodes.size());

        // Create a new canvas renderer which will render the sheet
        renderer = new CanvasRenderer(currentSheet, 50);
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
