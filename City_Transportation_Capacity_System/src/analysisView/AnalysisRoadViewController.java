package analysisView;

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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import chart.CreateChartServiceImpl;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.DBProcessor;
import main.GetInfo;
import main.Road;
import main.User;
import main.Region;

public class AnalysisRoadViewController implements Initializable{
	private Map<Integer, Road> allRoads;
	private Map<Integer, Region> allRegions;
	private DBProcessor dbProcessor = new DBProcessor();
	private User currentUser;
	private Map<Button, Road> itemAcesses;
	private static ArrayList<Road> selectRoads;
	@FXML
	private Label title;
	@FXML
	private Scene scene;
	@FXML
	private VBox sidebar;
	@FXML
	private Button searchViewBtn;
	@FXML
	private Button advSearchBtn;
	@FXML
	private Button analysisRoadBtn;
	@FXML
	private Button analysisCrossingBtn;
	@FXML
	private Button exitBtn;
	@FXML
	private TextField searchTF;
	@FXML
	private Button searchBtn;
	@FXML
	private VBox rootVBox;
	@FXML
	private VBox roadVBox;
	@FXML
	private HBox item;
	@FXML
	private Button itemAddBtn;
	@FXML
	private Label itemName;
	@FXML
	private ScrollPane scrollpane;
	@FXML
	private Button zztBtn;
	@FXML
	private Button bztBtn;

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
	
	public void initializeRoadAndRegion(Map<Integer, Road> roadlist,Map<Integer, Region> regionlist) {
		itemAcesses = new HashMap<>();
		selectRoads = new ArrayList<Road>();
		for (Integer key : regionlist.keySet()) {
			Region region = regionlist.get(key);
			roadVBox = new VBox();
			itemName = new Label(region.getRegion_name());
			item = new HBox();
			item.getChildren().add(itemName);
			roadVBox.getChildren().add(item);
			String [] roads = region.getRegion_road().split(",");
			for (int i = 0; i < roads.length; i++) {
				itemName = new Label(allRoads.get(Integer.parseInt(roads[i])).getRoad_name());
				itemAddBtn = new Button("添加");
				item = new HBox();
				item.getChildren().add(itemName);
				item.getChildren().add(itemAddBtn);
				roadVBox.getChildren().add(item);
				itemAcesses.put(itemAddBtn, allRoads.get(Integer.parseInt(roads[i])));
			}
			rootVBox.getChildren().add(roadVBox);
		}
		
		Set<Button> itemAddBtns = itemAcesses.keySet();
		for (Button button : itemAddBtns) {
			button.setOnAction(e -> {
				selectRoads.add(itemAcesses.get(button));
				button.setVisible(false);
			});
		}
	}
	
	public void handleZztResult(ActionEvent event) {
		CreateChartServiceImpl pm = new CreateChartServiceImpl();
		double[][] data = new double[][] {{0,0,0,0,0}};
		for (int i = 0; i < selectRoads.size(); i++) {
			switch (selectRoads.get(i).getRoad_laneNumber()) {
			case 2:
				data[0][0] = data[0][0]+1;
				break;
			case 3:
				data[0][1] = data[0][1]+1;
				break;
			case 4:
				data[0][2] = data[0][2]+1;
				break;
			case 5:
				data[0][3] = data[0][3]+1;
				break;
			case 6:
				data[0][4] = data[0][4]+1;
				break;
			default:
				break;
			}
		}
		pm.makeBarChart(data,"柱状图分析结果.jpg");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("已生成柱状图");
		alert.showAndWait();
	}
	
	public void handleBztResult(ActionEvent event) {
		CreateChartServiceImpl pm = new CreateChartServiceImpl();
		double[] data = new double[] {0,0};
		for (int i = 0; i < selectRoads.size(); i++) {
			switch (selectRoads.get(i).isRoad_left()+"") {
			case "true":
				data[0] = data[0]+1;
				break;
			case "false":
				data[1] = data[1]+1;
				break;
			default:
				break;
			}
		}
		pm.makePieChart(data,"饼状图分析结果.jpg");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("已生成饼状图");
		alert.showAndWait();
	}
	

	public void initialize(URL location, ResourceBundle resources) {
		allRoads = dbProcessor.fetchRoad();
		allRegions = dbProcessor.fetchRegion();
		currentUser = GetInfo.getCurrentUser();
		initializeRoadAndRegion(allRoads, allRegions);
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
