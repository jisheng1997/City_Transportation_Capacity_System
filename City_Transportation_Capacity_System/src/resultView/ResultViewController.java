package resultView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.DBProcessor;
import main.GetInfo;
import main.Road;
import showRoadView.ShowRoadViewController;

public class ResultViewController implements Initializable {
	
	private ArrayList<Road> results;
	private Map<Button, Road> itemAcesses;
	@FXML
	private Label title;
	@FXML
	private Button searchViewBtn;
	@FXML
	private Button advSearchViewBtn;
	@FXML
	private Button btn2;
	@FXML
	private Button exitBtn;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private VBox listVBox;
	@FXML
	private HBox item;
	@FXML
	private Label itemName;
	@FXML
	private Button itemOpenBtn;
	@FXML
	private VBox sidebar;

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
	
	public void intializeResult(ArrayList<Road> results) {
		this.results = results;
		itemAcesses = new HashMap<>();
		
		//Add all the items(hbox = name label + description label + open button) to the Map
		for (Road road : results) {
			itemName = new Label(road.getRoad_name());
			itemOpenBtn = new Button("打开");
			item = new HBox();
			item.getChildren().add(itemName);
			item.getChildren().add(itemOpenBtn);
			listVBox.getChildren().add(item);
			itemAcesses.put(itemOpenBtn, road);
		}
		
		//Register the listener for all the results items
		Set<Button> itemOpenBtns = itemAcesses.keySet();
		for (Button button : itemOpenBtns) {
			button.setOnAction(e -> {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("../showRoadView/showRoadView.fxml"));
				GetInfo.setCurrentRoad(itemAcesses.get(button));
				try {
					Scene scene = new Scene(fxmlLoader.load());
					ShowRoadViewController controller = fxmlLoader.getController();
					controller.setRoadDetail(GetInfo.getCurrentRoad());
					Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
					currentStage.setScene(scene);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
