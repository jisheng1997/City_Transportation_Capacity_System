package editRoadView;

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
import javafx.scene.control.ButtonType;
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

public class EditRoadViewController implements Initializable{
	private Map<Integer, Road> allRoads;
	private DBProcessor dbProcessor = new DBProcessor();
	private ArrayList<Road> results;
	private Road currentRoad;
	private User currentUser;
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
	@FXML
	private Label oldrn;
	
	
	public void handleEditRoadBtn(ActionEvent event) {
		String roadlaneNumber = roadlaneNumberTF.getText();		
		String roadtype = roadtypeCB.getSelectionModel().getSelectedItem();
		String roadspeed = roadspeedTF.getText();
		String isleft = roadleftCB.getSelectionModel().getSelectedItem();
		String roadcapacity = roadcapacityTF.getText();
		
		if (currentUser.isAdmin()) {
			if ((!roadlaneNumber.isEmpty())&&(!roadspeed.isEmpty())&&(!isleft.isEmpty())&&(!roadcapacity.isEmpty())&&(!roadtype.isEmpty())) {
				dbProcessor.editRoad(currentRoad.getRoad_name(),roadlaneNumber,roadtype,roadspeed,isleft,roadcapacity);
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("道路信息不完整！");
				alert.showAndWait();
			}
		}
	}
	
	@FXML
	public void handleDeleteRoadBtn(ActionEvent event) {
		if (currentUser.isAdmin()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("你确定要删除该路段吗");
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					dbProcessor.deleteRoad(GetInfo.getCurrentRoad().getRoad_id());
					alert.setContentText("删除成功");
					alert.showAndWait();
				}
			});
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("你没有管理员权限");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void handleAddRoadViewBtn(MouseEvent event) {
		if (currentUser.isAdmin()) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("../addRoadView/addRoadView.fxml"));
			try {
				Parent parent = fxmlLoader.load();
				Scene scene = new Scene(parent);
				Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				currentStage.setScene(scene);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("你没有管理员权限");
			alert.showAndWait();
		}
	}
	
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		allRoads = dbProcessor.fetchRoad();
		results = GetInfo.getResults();
		currentUser = GetInfo.getCurrentUser();
		currentRoad = GetInfo.getCurrentRoad();
		oldrn.setText(currentRoad.getRoad_name());
		roadtypeCB.setItems(FXCollections.observableArrayList("主干路","快速路","次干路","支路"));
		roadtypeCB.getSelectionModel().select(currentRoad.getRoad_type());
		roadleftCB.setItems(FXCollections.observableArrayList("是","否"));
		if(currentRoad.isRoad_left()) {
			roadleftCB.getSelectionModel().select("是");
		}else {
			roadleftCB.getSelectionModel().select("否");
		}
		roadlaneNumberTF.setText("" + currentRoad.getRoad_laneNumber()+ "");
		roadcapacityTF.setText("" + currentRoad.getRoad_capacity() + "");
		roadspeedTF.setText(currentRoad.getRoad_speed());
	}
	
}
