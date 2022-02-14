package com.graphics;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphicsManager extends Application {
    Line line = new Line();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicsManager.class.getResource("interface.fxml"));

        Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));
        Scene scene = new Scene(root);


        Circle circle1 = new Circle(50);
        circle1.setStroke(Color.GREEN);
        circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.7));
        circle1.relocate(100, 100);

        Circle circle2 = new Circle(50);
        circle2.setStroke(Color.BLUE);
        circle2.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.7));
        circle2.relocate(200, 200);

        Line line = new Line(circle1.getLayoutX(), circle1.getLayoutY(), circle2.getLayoutX(), circle2.getLayoutY());
        line.setStrokeWidth(20);

        Pane overlay = (Pane) scene.lookup("#mainPane");
        overlay.getChildren().addAll(circle1, circle2, line);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
