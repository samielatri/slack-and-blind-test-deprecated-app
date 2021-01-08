package old;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Client;
import model.Message;
import model.Profile;
import model.User;
import model.Workspace;
import utils.ProfileDao;
import utils.WorkplaceDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChatController implements Initializable {

    private static User user = new User();

    @FXML
    private TextField message;

    @FXML
    private TextArea M_output;

    @FXML
    private ListView<String> C_Output;

    @FXML
    private TextField channel;

    Message userMessage;

    Client client;

    ArrayList<Workspace> userWorkspaces = new ArrayList<>();

    ArrayList<Workspace> allWorkspaces = new ArrayList<>();

    private static Workspace activeworkspace = null;

    private static ArrayList<Message> messageslist = new ArrayList<>();

    @FXML
    void create(ActionEvent event) {

        String text = channel.getText();

        if (!text.isEmpty()) {
            Workspace workspace = new Workspace(text);
            workspace.setCreatedBy(user.getId());

            loadProfile(workspace, 0);
            userWorkspaces.add(workspace);
            updateClientWorkspace();
            closeStage();

        } else {
            new Alert(Alert.AlertType.INFORMATION, "Some Fields are Empty").showAndWait();
        }
    }

    @FXML
    void join(ActionEvent event) {
        boolean flag = false;
        String text = channel.getText();

        if (!text.isEmpty()) {

            for (Workspace workspace : allWorkspaces) {
                if (workspace.getName().equals(text)) {
                    flag = true;
                    loadProfile(workspace, 1);
                    userWorkspaces.add(workspace);
                    updateClientWorkspace();
                    closeStage();
                }
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Some Fields are Empty").showAndWait();
        }

        if (!flag) {
            new Alert(Alert.AlertType.INFORMATION, "No Workspace with given Name is Found").showAndWait();
        }

    }

    @FXML
    void send(ActionEvent event) {
        String text = message.getText();
        userMessage = new Message(user, text, activeworkspace);
        client.sendMessage(userMessage);
        message.setText("");
    }

    @FXML
    void select(MouseEvent event) {
        int selectedIndex = C_Output.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {

            activeworkspace = userWorkspaces.get(selectedIndex);

            Profile profile = ProfileDao.getProfileById(user.getId());
            user.setProfile(profile);

            Message message = new Message(user, "", activeworkspace);
            client.sendMessage(message);

            M_output.clear();
            for (Message messages : activeworkspace.getConversation()) {
                M_output.appendText(
                        messages.getSender().getProfile().getShownName() + ":" + messages.getContent() + "\n");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Profile profile = ProfileDao.getProfileById(user.getId());
        user.setProfile(profile);

        Message message = new Message(user, "New User Connected", activeworkspace);
        client = new Client(message, M_output);

        Thread t = new Thread(client);
        t.start();

        allWorkspaces = WorkplaceDao.getWorkspace();
        userWorkspaces = WorkplaceDao.getUserWorkspace(allWorkspaces, user.getId());

        for (Workspace workpace : userWorkspaces) {
            C_Output.getItems().add(workpace.getName());
        }

    }

    public static void setUser(User user) {
        ChatController.user = user;
    }

    public static void setMessages(ArrayList<Message> messages) {
        ChatController.messageslist = messages;
        activeworkspace.setConversation(messageslist);
    }

    public void updateClientWorkspace() {
        C_Output.getItems().clear();
        for (Workspace workpace : userWorkspaces) {
            C_Output.getItems().add(workpace.getName());
        }
    }

    private void loadProfile(Workspace workspace, int mode) {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../View/profile.fxml"));
            ProfileController deController = fxmlloader.getController();
            deController.setUser(user);
            deController.setWorkspace(workspace);
            deController.setMode(mode);

            // load the other stage
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            Scene scne = new Scene(root);
            stage.setScene(scne);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeStage() {
        ((Stage) message.getScene().getWindow()).close();
    }
}
