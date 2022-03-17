package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class controller  implements Initializable{

    @FXML
    private Button componants;

    @FXML
    private Button save;

    @FXML
    private Button files;

    @FXML
    private MenuButton menu;

    @FXML
    private Button nightView;

    @FXML
    private Button undo;

    @FXML
    private Button zoom1;

    @FXML
    private Button zoom2;
    
    @FXML
    private AnchorPane scroll;

    @FXML
    void Open_files(MouseEvent event) {

    }

    @FXML
    void cancel(MouseEvent event) {

    }

    @FXML
    void change_color(MouseEvent event) {

    }

    @FXML
    void sauvegarder(MouseEvent event) {

    }

    @FXML
    void decrease_zoom(MouseEvent event) {

    }

    @FXML
    void display_componants(MouseEvent event) {

    }

    @FXML
    void increase_zoom(MouseEvent event) {

    }

    @FXML
    void ouvrir_menu(MouseEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("menuView.fxml"));
		Scene fenetreMenu = new Scene(root);
		Stage myStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		myStage.setScene(fenetreMenu);
		myStage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
