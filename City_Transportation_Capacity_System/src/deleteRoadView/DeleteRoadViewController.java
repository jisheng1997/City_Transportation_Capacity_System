package deleteRoadView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DeleteRoadViewController implements Initializable{
	
	
	@FXML
	private Label title;
	@FXML
	private Button addRoadViewBtn;
	@FXML
	private Button editRoadViewBtn;
	@FXML
	private Button deleteRoadViewBtn;
	@FXML
	private Button exitBtn;
	@FXML
	private VBox sidebar;
	@FXML
	private TextField searchTF;
	@FXML
	private Button confirmBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
