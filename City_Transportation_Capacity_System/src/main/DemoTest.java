package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * The main appliction class for the application. This class create a cookbook object and start the login view
 * @author jisheng The class for testing search method
 */
public class DemoTest extends Application{

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            command line arguments; not used.
	 * 
	 */
	public static void main(String[] args) {
		
		GetInfo getInfo = new GetInfo();
		launch(args);
	}

	/**
	 * Override for the start mehtod of Application
	 * Present the login view
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		String url = "../loginView/loginView.fxml";
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource(url));
		Scene scene = new Scene(fxmlLoader.load());
		primaryStage.resizableProperty().set(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
