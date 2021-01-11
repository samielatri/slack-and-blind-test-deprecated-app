package Controllers;

import java.io.IOException;

import Model.User;
import Utils.LoginDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author user
 *
 *         This Class will handle the authentication phase of user according to
 *         their role
 * 
 */
public class LoginController {

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	private static User user;

	@FXML
	public void login(ActionEvent event) {
		String nameString = username.getText();
		String passString = password.getText();

		// password length should greater then 6 else give error
//		if (passString.length() >= 6) {

			user = LoginDao.login(nameString, passString);

			if (user != null) {
				new Alert(Alert.AlertType.INFORMATION, "Login Successfully").showAndWait();
				loadMenu();
			} else {
				new Alert(Alert.AlertType.INFORMATION, "Wrong Username or Password").showAndWait();
			}
//		} else {
//			new Alert(Alert.AlertType.INFORMATION, "Password Shoul Greater then 6").showAndWait();
//		}

	}

	private void loadMenu() {

		try {
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../View/Menu.fxml"));
			ChatController deController = fxmlloader.getController();
			deController.setUser(user);

			// load the other stage
			Parent root = (Parent) fxmlloader.load();
			Stage stage = new Stage();
			Scene scne = new Scene(root);
			stage.setScene(scne);
			stage.show();
			closeStage();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void register(ActionEvent event) {
		loadStage("../view/register.fxml", "Register");
		closeStage();
	}

	// this function takes the location of other stage and load that stage with
	// given title
	public void loadStage(String location, String title) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(location));
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();
			closeStage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// this function close the previous stage
	public void closeStage() {
		((Stage) username.getScene().getWindow()).close();
	}

}
