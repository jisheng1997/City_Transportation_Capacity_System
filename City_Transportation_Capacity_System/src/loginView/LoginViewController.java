package loginView;

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
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.DBProcessor;
import main.Road;
import main.User;

public class LoginViewController implements Initializable{
	
	private DBProcessor dbProcessor = new DBProcessor();
	private ArrayList<User> users;
	
	@FXML
	private VBox sidebar;
	@FXML
	private Button loginBtn;
	@FXML
	private Button registerBtn;
	@FXML
	private Button adminBtn;
	@FXML
	private Button exitBtn;
	@FXML
	private TextField usernameTF;
	@FXML
	private Button confirmBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private PasswordField passwordTF;
	@FXML
	private Label title1;


	@FXML
	public void handleLoginButtonAction(ActionEvent event) {
		String username = usernameTF.getText();
		String passwd = passwordTF.getText();
		boolean matched = false;
		
		System.out.println(username + passwd);

		for (User user : users) {
			// Initialize current user
			if (user.getUsername().equals(username) && user.getPassword().equals(passwd)) {
				System.out.println(user.getUsername());
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
				matched = true;
			} 
		}
		if(!matched) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("用户名或密码错误，请重试！");
			alert.showAndWait();
		}
	}
	
	/**
	 * Jump to register view
	 * 
	 * @param e MouseEvent
	 */
	@FXML
	public void handleRegisterBtn(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../registerView/registerView.fxml"));
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
//	/**
//	 * Jump to my favorite recipe list
//	 * 
//	 * @param e MouseEvent
//	 */
//	@FXML
//	public void handleAdminBtn(MouseEvent event) {
//		FXMLLoader fxmlLoader = new FXMLLoader();
//		fxmlLoader.setLocation(getClass().getResource("../adminView/adminView.fxml"));
//		try {
//			Parent parent = fxmlLoader.load();
//			Scene scene = new Scene(parent);
//			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//			currentStage.setScene(scene);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		users = dbProcessor.fetchUserInfo();
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
