package com.Graphics;

import com.Graphics.Canvas.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;

public class GraphicsManager extends Application {
    Line line = new Line();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicsManager.class.getResource("interface.fxml"));

        Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));
        Scene scene = new Scene(root);

        ScrollPane pane = (ScrollPane) scene.lookup("#workspace");
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);

        Sheet currentSheet = new Sheet(10, 10);
        CanvasRenderer renderer = new CanvasRenderer(currentSheet, 20);
        renderer.setCanvasParent(pane);

        SheetObject addDoor = new SheetObject("add", 1, Color.web("#33CC33"), 2, 1);

        NodeInstance.defaultNode = SheetObject.DefaultNode();

        currentSheet.addObject(new ComponentInstance(addDoor, 2, 2));

        AnimationTimer animate = new AnimationTimer() {
            @Override
            public void handle(long l) {
                renderer.renderGraphicContext();
            }
        };
        animate.start();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void CanvasAnimator() {

    }
}
