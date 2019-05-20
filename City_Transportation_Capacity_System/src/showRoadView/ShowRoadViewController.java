package showRoadView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.DBProcessor;
import main.GetInfo;
import main.Road;
import main.User;
import resultView.ResultViewController;

public class ShowRoadViewController implements Initializable{
	private ArrayList<Road> roadresults;
	private DBProcessor dbProcessor = new DBProcessor();
	private User currentUser;
	@FXML
	private Label title;
	@FXML
	private Button lastViewBtn;
	@FXML
	private Button editRoadBtn;
	@FXML
	private Button deleteRoadBtn;
	@FXML
	private Button addRoadBtn;
	@FXML
	private VBox sidebar;
	@FXML
	private Text rnText;
	@FXML
	private Text rlText;
	@FXML
	private Text rtText;
	@FXML
	private Text rsText;
	@FXML
	private Text riText;
	@FXML
	private Text rbText;
	
	public void setRoadDetail(Road road) {
		GetInfo.setCurrentRoad(road);
		rnText.setText(road.getRoad_name());
		rlText.setText(""+road.getRoad_laneNumber()+"");
		rtText.setText(road.getRoad_type());
		rsText.setText(road.getRoad_speed());
		if (road.isRoad_left()) {
			riText.setText("是");
		}else {
			riText.setText("否");
		}
		rbText.setText(""+road.getRoad_capacity()+"");
	}
	
	public void lastView(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../resultView/resultView.fxml"));
		try {
			Parent parent = fxmlLoader.load();			
			ResultViewController controller = fxmlLoader.getController();
			controller.initalizeResult(roadresults);
			Scene scene = new Scene(parent);
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void initializeResults(ArrayList<Road> results) {
		roadresults = results;
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
	
	@FXML
	public void handleEditRoadViewBtn(MouseEvent event) {
		if (currentUser.isAdmin()) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("../editRoadView/editRoadView.fxml"));
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    currentUser = GetInfo.getCurrentUser();
	}
}
