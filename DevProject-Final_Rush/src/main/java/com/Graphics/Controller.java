package com.Graphics;

import com.Graphics.Workspace.Application.SheetObject;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Physics.Component;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;

import static com.Graphics.GraphicsManager.currentSheet;
import static com.Graphics.GraphicsManager.sheet;

public class Controller {

    public Slider InputSlider;
    public Slider OutputSlider;
    public Slider ScaleSlider;

    @FXML
    private MenuItem And;

    @FXML
    private MenuItem No;

    @FXML
    private MenuItem Or;

    @FXML
    private Button Simulate;

    @FXML
    private MenuItem config;

    @FXML
    private Button files;

    @FXML
    private MenuButton menu;

    @FXML
    private Button save;

    @FXML
    private Button test;

    @FXML
    private ScrollPane workspace;

    @FXML
    private BorderPane pane;

    @FXML
    private AnchorPane anchor;
    @FXML
    private SplitMenuButton componant;




    // We create 3 new components, which are the most basic ones we'll be using
    SheetObject orDoor = new SheetObject(0, "or", Color.PINK, 2, 1);
    SheetObject noDoor = new SheetObject(1, "not", Color.web("#772288"), 1, 1);
    SheetObject andDoor = new SheetObject(2, "and", Color.web("#03C93C"), 2, 1);


    public void no(ActionEvent actionEvent) {
        Component physicNo = new Component("no",1, 1, new Boolean[] {true,false});
        sheet.addComponent(physicNo);
        ComponentInstance no = new ComponentInstance(noDoor, 12, 6, physicNo);
        currentSheet.addObject(no);
    }

    public void or(ActionEvent actionEvent) {
        Component physicOr = new Component("or",1, 1, new Boolean[]{false, true, true, true});
        sheet.addComponent(physicOr);
        ComponentInstance or = new ComponentInstance(orDoor, 12, 6, physicOr);
        currentSheet.addObject(or);
    }

    public void and(ActionEvent actionEvent) {
        Component physicAnd = new Component("and",1, 1, new Boolean[] {false, false, false, true});
        sheet.addComponent(physicAnd);
        ComponentInstance and = new ComponentInstance(andDoor, 12, 6, physicAnd);
        currentSheet.addObject(and);
    }


    public void test(ActionEvent actionEvent) {
        System.out.println(InputSlider.getValue());
    }

    public void inputs(MouseEvent mouseEvent) {
        // On calcule le nombre d'entrées à placer
        int toPlace = (int) InputSlider.getValue() - currentSheet.ioComponent.startNodes.size();
        // Si il faut ajouter des entrées
        if (toPlace > 0) {
            // On ajoute ce nombre d'entrées
            for (int i = 0; i < toPlace; i++) {
                currentSheet.ioComponent.addStartNode(currentSheet);
            }
        }
        // Sinon, si il faut en enlever
        else if (toPlace < 0) {
            // On enlève ce nombre d'entrées
            for (int i=0;i<-toPlace;i++) {
                currentSheet.ioComponent.delStartNode(currentSheet.ioComponent.startNodes.get(currentSheet.ioComponent.startNodes.size() - 1), currentSheet);
            }
        }
    }

    public void outputs(MouseEvent mouseEvent) {
        // On calcule le nombre d'entrées à placer
        int toPlace = (int) OutputSlider.getValue() - currentSheet.ioComponent.endNodes.size();
        // Si il faut ajouter des entrées
        if (toPlace > 0) {
            // On ajoute ce nombre d'entrées
            for (int i = 0; i < toPlace; i++) {
                currentSheet.ioComponent.addEndNode(currentSheet);
            }
        }
        // Sinon, si il faut en enlever
        else if (toPlace < 0) {
            // On enlève ce nombre d'entrées
            for (int i=0;i<-toPlace;i++) {
                currentSheet.ioComponent.delEndNode(currentSheet.ioComponent.endNodes.get(currentSheet.ioComponent.endNodes.size() - 1), currentSheet);
            }
        }
    }

    public void scale(MouseEvent mouseEvent) {
        //On calcule la nouvelle échelle à appliquer
        double newScale = ScaleSlider.getValue();
        GraphicsManager.renderer.setScale(newScale);
    }

    public void simulate(ActionEvent actionEvent) {
        sheet.refresh();
    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Confirm Exit");
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane,400,100);
        Label label = new Label("Are you sure ?");
        label.setLayoutX(160);
        label.setLayoutY(10);
        Button DoNotSave = new Button("Do not save and exit");
        DoNotSave.setOnAction(actionEvent -> Platform.exit());
        DoNotSave.setLayoutY(50);
        DoNotSave.setLayoutX(20);
        Button stay = new Button("stay");
        stay.setOnAction(actionEvent -> stage.close());
        stay.setLayoutY(50);
        stay.setLayoutX(190);
        Button SaveExit = new Button("save and exit");
        SaveExit.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
                File savingFile = fc.showSaveDialog(new Stage());
                try {
                    savingFile.createNewFile();
                    Platform.exit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        SaveExit.setLayoutY(50);
        SaveExit.setLayoutX(280);
        anchorPane.getChildren().addAll(label,DoNotSave,stay,SaveExit);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }


    @FXML
    void open_files(MouseEvent event) {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(new Stage()).getAbsoluteFile();
    }

    @FXML
    void save_files(MouseEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File savingFile = fc.showSaveDialog(new Stage());
        savingFile.createNewFile();
    }


}