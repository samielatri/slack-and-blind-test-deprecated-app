package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.User;
import Model.Workspace;
import Utils.LoginDao;
import Utils.WorkplaceDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ChatController implements Initializable {s
    private String host = "localhost";
    private int port = 62000;

    private static User user = new User();

    // For Socket Connection
    Socket socket;
    ObjectInputStream input = null;
    ObjectOutputStream output = null;

    @FXML
    private TextField message;

    @FXML
    private TextArea M_output;

    @FXML
    private TextArea C_Output;

    @FXML
    private TextField channel;

    @FXML
    void create(ActionEvent event) {

        String text = channel.getText();
        Workspace workspace = new Workspace(text);
//		workspace.setCreatedBy(user.getId());
        workspace.setCreatedBy(1);

//		ArrayList<User> admins = workspace.getAdmins();
//		admins.add(user);
//		workspace.setAdmins(admins);

        boolean createWorkspace = WorkplaceDao.createWorkspace(workspace);

        if (createWorkspace) {
            new Alert(Alert.AlertType.INFORMATION, "Created Successfully").showAndWait();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Error While Creating").showAndWait();
        }

    }

    @FXML
    void join(ActionEvent event) {

    }

    @FXML
    void send(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//		try {
//			InetAddress ip = InetAddress.getByName(host);
//
//			socket = new Socket(ip, port);
//
//			output = new ObjectOutputStream(socket.getOutputStream());
//
//			input = new ObjectInputStream(socket.getInputStream());
//
//			output.writeObject(user);
//
//			output.flush();
//
//			System.out.println("client.Client  ");
//
////			while (!(socket.isClosed())) {
////
////			}
//
//		} catch (IOException ex) {
//			System.out.println("client.Client" + ex.getMessage());
//
//		}
    }

    public static void setUser(User user) {
        ChatController.user = user;
    }
}