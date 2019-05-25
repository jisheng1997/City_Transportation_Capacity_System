package registerView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.DBProcessor;
import main.GetInfo;
import main.User;

public class RegisterViewController implements Initializable {
	
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
	private Label unReminderLabel;
	@FXML
	private Label pwReminderLabel;
	@FXML
	private Label confpwReminderLabel;
	@FXML
	private PasswordField passwordTF;
	@FXML
	private PasswordField confirmpasswordTF;
	@FXML
	private Label title1;
	
	private Boolean isRegisterNameValid;
	private Boolean isPasswordPaired;
	private Boolean isRegisterPasswdValid;

	// Event Listener on Button[#loginBtn].onMouseClicked
	@FXML
	public void handleloginViewBtn(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../loginView/loginView.fxml"));
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
	public void handleRegisterButtonAction(ActionEvent event) {
		String username = usernameTF.getText();
		String passwd = passwordTF.getText();
		String confirmpasswd = confirmpasswordTF.getText();
		boolean isExist = false;
		
		
		if (username.length() == 0) {
			unReminderLabel.setText("用户名不能为空！");
			unReminderLabel.setVisible(true);
			isRegisterNameValid = false;
		}else {
			unReminderLabel.setVisible(false);
			isRegisterNameValid = true;
		}
		
		if (passwd.length() == 0) {
			pwReminderLabel.setText("密码不能为空！");
			pwReminderLabel.setVisible(true);
			isRegisterPasswdValid = false;
		}else {
			pwReminderLabel.setVisible(false);
			isRegisterPasswdValid = true;
		}
		
		if (confirmpasswd.length() == 0) {
			confpwReminderLabel.setText("确认密码不能为空！");
			confpwReminderLabel.setVisible(true);
			isPasswordPaired = false;
		}else if(passwd == confirmpasswd) {
				confpwReminderLabel.setText("两次密码不一致！");
				confpwReminderLabel.setVisible(true);
				isPasswordPaired = false;
		}else{
			confpwReminderLabel.setVisible(false);
			isPasswordPaired = true;
		}
		
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				isExist = true;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("用户名已存在!");
				alert.show();
			}
		}
		System.out.println(passwd+confirmpasswd);
		if (!isExist) {
			if (isRegisterNameValid && isPasswordPaired && isRegisterPasswdValid) {
				User newUser = new User(username, passwd,false);
				dbProcessor.insertUser(newUser);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("注册成功！");
				alert.setHeaderText("Information");
				alert.show();
				this.users = dbProcessor.fetchUserInfo();
				GetInfo.setCurrentUser(newUser);
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
			}else {
				
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		users = dbProcessor.fetchUserInfo();
		confpwReminderLabel.setVisible(false);
		unReminderLabel.setVisible(false);
		pwReminderLabel.setVisible(false);
		isRegisterNameValid = false;
		isPasswordPaired = false;
		isRegisterPasswdValid = false;
		
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
