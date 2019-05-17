package editRoadView;



import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditRoadViewController implements Initializable{
	
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
	private TextField roadnameTF;
	@FXML
	private TextField roadlaneNumberTF;
	@FXML
	private TextField roadtypeTF;
	@FXML
	private TextField roadspeedTF;
	@FXML
	private TextField roadleftTF;
	@FXML
	private TextField roadcapacityTF;
	@FXML
	private Label oldrn;
	@FXML
	private Label oldrln;
	@FXML
	private Label oldrt;
	@FXML
	private Label oldrs;
	@FXML
	private Label oldrc;
	@FXML
	private Label oldrl;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
}
