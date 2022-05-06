package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class HelloController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private AnchorPane sheet;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuButton menu;

    @FXML
    private MenuItem menuFile;

    @FXML
    private CheckMenuItem nightOff;

    @FXML
    private CheckMenuItem nightOn;

    @FXML
    private MenuButton nightView;


    @FXML
    private Button componants;

    @FXML
    private Button test;

    @FXML
    private Button files;


    @FXML
    private Button save;

    @FXML
    private Button undo;

    @FXML
    private Button zoom1;

    @FXML
    private Button zoom2;



    @FXML
    void cancel(MouseEvent event) {

    }



    @FXML
    void decrease_zoom(MouseEvent event) {
        Scale scale = new Scale();
        scale.setX(0.975);
        scale.setY(0.975);
        sheet.getTransforms().add(scale);
    }

    @FXML
    void increase_zoom(MouseEvent event) {
        Scale scale = new Scale();
        scale.setX(1.025);
        scale.setY(1.025);
        sheet.getTransforms().add(scale);
    }

    @FXML
    void open_componant(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        Scene fenetreMenu = new Scene(fxmlLoader.load(), 400, 300);
        //Stage myStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage myStage = new Stage();
        myStage.setTitle("Componant list");
        myStage.setScene(fenetreMenu);
        myStage.show();
    }

    @FXML
    void open_files(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.showOpenDialog(new Stage());
    }

    @FXML
    void open_menu(MouseEvent event) throws IOException {

    }

    @FXML
    void save_file(MouseEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
       File savingFile = fc.showSaveDialog(new Stage());
       savingFile.createNewFile();
    }
    @FXML
    void normal_color(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        anchor.getChildren().set(0,pane);

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
    void open_settings(ActionEvent event) {

    }


    public void change_color(ActionEvent actionEvent) throws NullPointerException, IOException {

        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view-Night.fxml")));
        anchor.getChildren().set(0,pane);

    }

}
