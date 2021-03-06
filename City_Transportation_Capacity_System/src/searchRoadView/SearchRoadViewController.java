package searchRoadView;

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

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.DBProcessor;
import main.Road;
import resultView.ResultViewController;

public class SearchRoadViewController implements Initializable {
	
	private DBProcessor dbProcessor = new DBProcessor();
	private Map<Integer, Road> allRoads;

	@FXML
	private Label title;
	@FXML
	private VBox sidebar;
	@FXML
	private Button searchViewBtn;
	@FXML
	private Button advSearchBtn;
	@FXML
	private Button btn2;
	@FXML
	private Button exitBtn;
	@FXML
	private TextField searchTF;
	@FXML
	private Button searchBtn;

	// Event Listener on Button[#advSearchBtn].onMouseClicked
	@FXML
	public void handleAdvSearchRoadViewBtn(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../advsearchRoadView/advSearchRoadView.fxml"));
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	// Event Listener on Button[#searchBtn].onAction
	@FXML
	public void handleSearchResult(ActionEvent event) {
		
		ArrayList<Road> results = new ArrayList<>();
		String content = this.searchTF.getText();
		results = searchRoadByName(content);
		if (!results.isEmpty()) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("../resultView/resultView.fxml"));
			try {
				Parent parent = fxmlLoader.load();
				ResultViewController controller = fxmlLoader.getController();
				controller.intializeResult(results);
				Scene scene = new Scene(parent);
				Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				currentStage.setScene(scene);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
	}
	
	public ArrayList<Road> searchRoadByName(String roadname) {
		ArrayList<Integer> roadIds = dbProcessor.matchRoadName(roadname);
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
		exitBtn.setOnMouseClicked(e -> {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("����Ҫ�˳�������?");
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
					currentStage.close();
				}
			});
		});
	}
}
