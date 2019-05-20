package addRoadView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.DBProcessor;
import main.GetInfo;
import main.Road;
import main.User;
import resultView.ResultViewController;

public class AddRoadViewController implements Initializable{
	private Map<Integer, Road> allRoads;
	private DBProcessor dbProcessor = new DBProcessor();
	private ArrayList<Road> results;
	private User currentUser;
	private Road currentRoad;
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
	private ChoiceBox<String> roadtypeCB;
	@FXML
	private TextField roadspeedTF;
	@FXML
	private ChoiceBox<String> roadleftCB;
	@FXML
	private TextField roadcapacityTF;
	
	
	public void lastView(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../resultView/resultView.fxml"));
		try {
			Parent parent = fxmlLoader.load();			
			ResultViewController controller = fxmlLoader.getController();
			controller.initalizeResult(results);
			Scene scene = new Scene(parent);
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void handleAddRoadBtn(ActionEvent event) {
		String roadname = roadnameTF.getText();
		String roadlaneNumber = roadlaneNumberTF.getText();
		String roadspeed = roadspeedTF.getText();
		String isleft = roadleftCB.getSelectionModel().getSelectedItem();
		String roadcapacity = roadcapacityTF.getText();
		
		if (currentUser.isAdmin()) {
			if ((!roadname.isEmpty())&&(!roadlaneNumber.isEmpty())&&(!roadspeed.isEmpty())&&(!isleft.isEmpty())&&(!roadcapacity.isEmpty())) {
				for (int i = 0;i<allRoads.size();i++) {
					if (allRoads.get(i).getRoad_name().equals(roadname)) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("用户名已存在!");
						alert.show();
					}
				}
				dbProcessor.addRoad(roadname,roadlaneNumber,roadcapacity,isleft,roadcapacity);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("添加道路成功！");
				alert.setHeaderText("Information");
				alert.show();
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("道路信息不完整！");
				alert.showAndWait();
			}
		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("你没有管理员权限");
			alert.showAndWait();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		allRoads = dbProcessor.fetchRoad();
		results = GetInfo.getResults();
		currentRoad = GetInfo.getCurrentRoad();
		currentUser = GetInfo.getCurrentUser();
		roadtypeCB.setItems(FXCollections.observableArrayList("主干路","快速路","次干路","支路"));
		roadtypeCB.getSelectionModel().select(0);
		roadleftCB.setItems(FXCollections.observableArrayList("是","否"));
		roadleftCB.getSelectionModel().select(0);
	}
}
