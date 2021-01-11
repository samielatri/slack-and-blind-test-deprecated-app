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

public class RegisterController {

	@FXML
	private PasswordField password;

	@FXML
	private TextField email;

	@FXML
	void register(ActionEvent event) {

		String Email = email.getText();
		String pass = password.getText();

		boolean register = LoginDao.register(Email,pass);
		if (register) {
			new Alert(Alert.AlertType.INFORMATION, "Register Successfully").showAndWait();
			loadStage("../view/login.fxml", "Login");
			closeStage();
		} else {
			new Alert(Alert.AlertType.INFORMATION, "Error While Registering").showAndWait();
		}

	}

	@FXML
	void login(ActionEvent event) {
		loadStage("../view/login.fxml", "Login");
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
		((Stage) email.getScene().getWindow()).close();
	}

}
