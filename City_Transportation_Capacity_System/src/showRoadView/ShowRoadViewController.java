package showRoadView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.GetInfo;
import main.Road;
import resultView.ResultViewController;

public class ShowRoadViewController implements Initializable{
	
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
		rnText.setText(road.getRoad_name());
		rlText.setText(""+road.getRoad_laneNumber()+"");
		rtText.setText(road.getRoad_type());
		rsText.setText(road.getRoad_speed());
		if (road.isRoad_left()) {
			riText.setText("ÊÇ");
		}else {
			riText.setText("·ñ");
		}
		rbText.setText(""+road.getRoad_capacity()+"");
	}
	
	public void lastView(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../resultView/resultView.fxml"));
		try {
			ResultViewController controller = fxmlLoader.getController();
			controller.initalizeResult(GetInfo.getResults());
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
