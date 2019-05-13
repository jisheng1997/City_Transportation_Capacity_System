package showRoadView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Road;

public class ShowRoadViewController implements Initializable{
	
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
