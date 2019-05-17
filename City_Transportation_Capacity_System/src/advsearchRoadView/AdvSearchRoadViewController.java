package advsearchRoadView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.DBProcessor;
import main.Road;
import resultView.ResultViewController;
import javafx.scene.control.ChoiceBox;

public class AdvSearchRoadViewController implements Initializable{
	
	private DBProcessor dbProcessor = new DBProcessor();
	private Map<Integer, Road> allRoads;
	
	@FXML
	private Label title;
	@FXML
	private VBox sidebar;
	@FXML
	private Button searchViewBtn;
	@FXML
	private Button advSearchViewBtn;
	@FXML
	private Button btn2;
	@FXML
	private Button exitBtn;
	@FXML
	private ChoiceBox<String> road_laneNumberCB;
	@FXML
	private ChoiceBox<String> road_typeCB;
	@FXML
	private ChoiceBox<String> leftCB;
	@FXML
	private TextField speedTF;
	@FXML
	private TextField capacityTF;
	@FXML
	private Button confirmBtn;

	// Event Listener on Button[#searchViewBtn].onMouseClicked
	@FXML
	public void handleSearchRoadViewBtn(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../searchRoadView/searchRoadView.fxml"));
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	// Event Listener on Button[#confirmBtn].onAction
	@FXML
	public void handleAdvSearchResult(ActionEvent event) {
		ArrayList<Road> results = new ArrayList<>();
		String road_laneNumber = road_laneNumberCB.getSelectionModel().getSelectedItem();
		String road_type = road_typeCB.getSelectionModel().getSelectedItem();
		String isleft = leftCB.getSelectionModel().getSelectedItem();
		String speed =  speedTF.getText();
		String capacity = capacityTF.getText();
		
		results = searchRoad(road_laneNumber, road_type, isleft, speed, capacity);
		if (!results.isEmpty()) {
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
	}
	
	public ArrayList<Road> searchRoad(String road_laneNumber,String road_type,String isleft,String speed,String capacity) {
		ArrayList<Integer> roadIds = dbProcessor.matchRoad(road_laneNumber,road_type,isleft,speed,capacity);
		ArrayList<Road> results = new ArrayList<Road>();
		if (!roadIds.isEmpty()) {
			for (Integer roadId : roadIds) {
				results.add(allRoads.get(roadId));
			}
		}
		return results;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		allRoads = dbProcessor.fetchRoad();
		road_laneNumberCB.setItems(FXCollections.observableArrayList("任意","2","3","4"));
		road_laneNumberCB.getSelectionModel().select(0);
		road_typeCB.setItems(FXCollections.observableArrayList("任意","主干路","快速路",""));
		road_typeCB.getSelectionModel().select(0);
		leftCB.setItems(FXCollections.observableArrayList("任意","是","否"));
		leftCB.getSelectionModel().select(0);
		
		exitBtn.setOnMouseClicked(e -> {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("你想要退出程序吗?");
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
					currentStage.close();
				}
			});
		});
	}
}
