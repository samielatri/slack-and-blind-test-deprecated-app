package controller.user;

import database.AbstractSQLDAO;
import database.DAO;
import database.DAOFactory;
import model.SlackSystem;
import model.user.Profile;

import java.sql.SQLException;
import java.util.HashMap;


public class ProfileServiceDAO {
    private SlackSystem slackSystem=new SlackSystem();
    private DAO<Profile> DAOProfile;
    // you can visit a profile only when you are in the workspace
    public Profile visitProfile(String profileToVisitId) throws SQLException {

        Profile profileToVisit = null;

        // verify if user is connected
        if (slackSystem.getCurrentConnectedUser() == null) {
            System.out.println("No user connected !");
        }

        // verify if user is connected to a workspace
        if (slackSystem.getCurrentConnectedWorkspace() == null){
            System.out.println("User not connected to any workspace !");
        }

        // verify if user has a profile in the current workspace
        if (slackSystem.getCurrentConnectedProfile() == null){
            System.out.println("User does not have a profile in the current workspace !");
        } else {
            if (DAOProfile.select(profileToVisitId) == null){
                System.out.println("Database error, does not exist");
            } else {
                if(DAOProfile.select(profileToVisitId).equals(slackSystem.getCurrentConnectedProfile())){
                    System.out.println("Visiting own profile");
                    profileToVisit = slackSystem.getCurrentConnectedProfile();
                } else {
                    System.out.println("Visiting another profile");
                    profileToVisit = DAOProfile.select(profileToVisitId);
                }
            }
        }

        return profileToVisit;
    }

    /**   OK
     *
     * @param inputtedCharacteristics attributes entered by the user
     * @return
     * @throws SQLException
     */


    public Profile updateProfile(String... inputtedCharacteristics) throws SQLException {

        if (Profile.getNumberCharacteristics() != inputtedCharacteristics.length){
            System.out.println("Problem with the inputted parameter");
            return null;
        }

        String[] profileCharacteristicsKeys = {
                "username",
                "currentStatus",
                "completeName",
                "shownName",
                "actualWorkPosition",
                "phoneNumber",
                "skypeUserName",
                "timeZone"
        };

        Profile currentConnectedProfile = slackSystem.getCurrentConnectedProfile();
        HashMap<String, String> currentProfileCharacteristics = new HashMap<>();

        for (int i = 0; i < inputtedCharacteristics.length; i++){
            if (inputtedCharacteristics[i] == null){
                inputtedCharacteristics[i] = "";
            }
            currentProfileCharacteristics.put(profileCharacteristicsKeys[i], inputtedCharacteristics[i]);
        }

        if (currentProfileCharacteristics.get("username") != "" && currentConnectedProfile.getUsername().equals(currentProfileCharacteristics.get("username"))) {
            currentConnectedProfile.setUsername(currentProfileCharacteristics.get("username"));
        }

        if (currentProfileCharacteristics.get("currentStatus") != "" && currentConnectedProfile.getCurrentStatus().equals(currentProfileCharacteristics.get("currentStatus"))) {
            currentConnectedProfile.setCurrentStatus(currentProfileCharacteristics.get("currentStatus"));
        }

        if (currentProfileCharacteristics.get("completeName") != "" && currentConnectedProfile.getCompleteName().equals(currentProfileCharacteristics.get("completeName"))) {
            currentConnectedProfile.setCompleteName(currentProfileCharacteristics.get("completeName"));
        }

        if (currentProfileCharacteristics.get("shownName") != "" && currentConnectedProfile.getShownName().equals(currentProfileCharacteristics.get("shownName"))) {
            currentConnectedProfile.setShownName(currentProfileCharacteristics.get("shownName"));
        }

        if (currentProfileCharacteristics.get("actualWorkPosition") != "" && currentConnectedProfile.getActualWorkPosition().equals(currentProfileCharacteristics.get("actualWorkPosition"))) {
            currentConnectedProfile.setActualWorkPosition(currentProfileCharacteristics.get("actualWorkPosition"));
        }

        if (currentProfileCharacteristics.get("phoneNumber") != "" && currentConnectedProfile.getPhoneNumber().equals(currentProfileCharacteristics.get("phoneNumber"))) {
            currentConnectedProfile.setPhoneNumber(currentProfileCharacteristics.get("phoneNumber"));
        }

        if (currentProfileCharacteristics.get("skypeUserName") != "" && currentConnectedProfile.getSkypeUserName().equals(currentProfileCharacteristics.get("skypeUserName"))) {
            currentConnectedProfile.setSkypeUserName(currentProfileCharacteristics.get("skypeUserName"));
        }

        if (currentProfileCharacteristics.get("timeZone") != "" && currentConnectedProfile.getTimezone().equals(currentProfileCharacteristics.get("timeZone"))) {
            currentConnectedProfile.setTimezone(currentProfileCharacteristics.get("timeZone"));
        }

        DAOProfile.update(DAOProfile.select(currentConnectedProfile.getId()));

        return currentConnectedProfile;
    }




    /**************************************************************************











    public void addCollaborator(){
        User user = slackSystem.getConnectedUser();
        Profile profile = user.getCurrentProfile();
        Workspace currentWorkspace = profile.getWorkspace();
        ArrayList<User> members = currentWorkspace.getMemberProfiles();

        ArrayList<Profile> collaborators = profile.getCollaborators();
        String collaboratorShownName = readString("name of the collaborator");
        for(Profile collaborator : collaborators){
            if(collaborator.getShownName() == collaboratorShownName){
                System.out.println("Collaborator already exists");
                return;
            }
            for(User member : members){
                ArrayList<Profile> memberProfiles = member.getProfiles();
                for(Profile memberProfile : memberProfiles){
                    if (memberProfile.getShownName() == collaboratorShownName) {
                        System.out.println("Adding collaborator...");
                        profile.getCollaborators().add(memberProfile);
                        System.out.println("Collaborator added");
                        return;
                    }
                }
            }
        }
        System.out.println("Collaborator does not exist in the workspace");
    }

    public void visitCollaboratorProfile(){
        User user = slackSystem.getConnectedUser();
        Profile profile = user.getCurrentProfile();
        Workspace currentWorkspace = profile.getWorkspace();
        ArrayList<User> members = currentWorkspace.getMemberProfiles();

        ArrayList<Profile> collaborators = profile.getCollaborators();
        String collaboratorShownName = readString("name of the collaborator");
        for(Profile collaborator : collaborators){
            if(collaborator.getShownName() == collaboratorShownName){
                System.out.println("visiting collaborator...");
                System.out.println(collaborator.toString());
                System.out.println("Collaborator visited");
                return;
            }
        }
        System.out.println("Collaborator does not exist");
    }

    public void removeCollaborator(){
        User user = slackSystem.getConnectedUser();
        Profile profile = user.getCurrentProfile();
        Workspace currentWorkspace = profile.getWorkspace();
        ArrayList<User> members = currentWorkspace.getMemberProfiles();

        ArrayList<Profile> collaborators = profile.getCollaborators();
        String collaboratorShownName = readString("name of the collaborator");
        for(Profile collaborator : collaborators){
            if(collaborator.getShownName() == collaboratorShownName){
                System.out.println("deleting collaborator...");
                profile.getCollaborators().remove(collaborator);
                System.out.println("Collaborator deleted");
                return;
            }
        }
        System.out.println("Collaborator does not exist");
    }*/

    //called by a user
    public Profile createProfile(String idUsr,String idWs){
        Profile profile = new Profile(idWs,idUsr);
        String id = idUsr+"."+idWs;
        profile.setUserId(idUsr);
        profile.setWorkspaceId(idWs);
        return profile;
    }



     /****************************************************************/

}


