package old;

import java.io.IOException;
import java.util.ArrayList;

import model.Profile;
import model.User;
import model.Workspace;
import utils.ProfileDao;
import utils.WorkplaceDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProfileController {

    @FXML
    private TextField phone;

    @FXML
    private TextField completename;

    @FXML
    private TextField workposition;

    @FXML
    private TextField shownname;

    @FXML
    private TextField timezone;

    private static User user = new User();

    private static Workspace workspace = new Workspace();;

    private static int mode;

    @FXML
    void register(ActionEvent event) {

        String Phone = phone.getText();
        String completeName = completename.getText();
        String workPosition = workposition.getText();
        String shownName = shownname.getText();
        String timeZone = timezone.getText();

        Profile profile = new Profile();

        if (!(Phone.isEmpty() || completeName.isEmpty() || workPosition.isEmpty() || shownName.isEmpty()
                || timeZone.isEmpty())) {

            profile.setCompleteName(completeName);
            profile.setShownName(shownName);
            profile.setActualWorkPosition(workPosition);
            profile.setPhoneNumber(Phone);
            profile.setTimezone(timeZone);
            profile.setCurrentStatus("online");

            user.setProfile(profile);

            int profileId = ProfileDao.createProfile(user);

            if (profileId != 0) {
                profile.setId(profileId);
                user.setProfile(profile);
                ChatController.setUser(user);

                if (mode == 0) {
                    CreateWorkspace();
                } else {
                    UpdateWorkspace();
                }

                loadStage("../view/Menu.fxml", "Chat Menu");
                closeStage();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Error While Registering").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Some Fields are Empty").showAndWait();
        }

    }

    public void CreateWorkspace() {
        ArrayList<User> admins = workspace.getAdmins();
        admins.add(user);
        workspace.setAdmins(admins);

        boolean createWorkspace = WorkplaceDao.createWorkspace(workspace);

        if (createWorkspace) {

        } else {

        }
    }

    public void UpdateWorkspace() {

        try {
            workspace.getMembers().add(user);
            WorkplaceDao.updateWorkspace(workspace, user.getId());

        } catch (Exception e) {

        }

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
        ((Stage) completename.getScene().getWindow()).close();
    }

    public static void setUser(User user) {
        ProfileController.user = user;
    }

    public static void setWorkspace(Workspace workspace) {
        ProfileController.workspace = workspace;
    }

    public static void setMode(int mode) {
        ProfileController.mode = mode;
    }

}
