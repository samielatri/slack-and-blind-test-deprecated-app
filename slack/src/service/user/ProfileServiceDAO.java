package service.user;

import model.communication.Workspace;
import model.user.Profile;
import service.AbstractServiceDAO;
import tool.Keyboard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProfileServiceDAO extends AbstractServiceDAO {

    // you can visit a user only when you are in the workspace
    public void visitProfile() throws SQLException {
        String keyPressed = "";
        Scanner scannerKeyPressed = null;

        if (currentConnectedProfile == null) {
            System.out.println("No user connected !");
            return ;
        }

        Workspace currentWorkspace = DAOWorkspace.select(currentConnectedWorkspace.getId());
        if (currentWorkspace == null){
            System.out.println("User not connected to any workspace !");
            return ;
        }

        Profile currentProfile = currentConnectedProfile;
        if (currentProfile == null){
            System.out.println("User does not have a user in the current workspace !");
            return ;
        }

        System.out.println(currentProfile.toString());
        System.out.println("Press any key to come back");
        System.out.print("> ");
        while(keyPressed == "" || keyPressed == null) {
            scannerKeyPressed = new Scanner(System.in);
            keyPressed = scannerKeyPressed.nextLine();
        }
    }

    public void updateProfile() throws SQLException {
        String keypressed = "";
        Scanner scannerKeypressed = null;
        Profile connectedProfile = DAOUser.select(currentConnectedProfile.getId());

        if (connectedProfile == null) {
            System.out.println("No user connected !");
            return ;
        }

        Workspace currentWorkspace = DAOWorkspace.select(currentConnectedWorkspace.getId());
        if (currentWorkspace == null){
            System.out.println("User not connected to any workspace !");
            return ;
        }

        Profile currentProfile = DAOProfile.select(currentConnectedProfile.getId());
        if (currentProfile == null){
            System.out.println("User does not have a user in the current workspace !");
            return ;
        }

        int choice = 0;
        do {
            System.out.println(currentProfile.toString() + "\n9-Back");
        /*
            ", 1 - username='" + username + '\'' +
            ", 2 - currentStatus='" + currentStatus + '\'' +
            ", 3 - completeName='" + completeName + '\'' +
            ", 4 - shownName='" + shownName + '\'' +
            ", 5 - actualWorkPosition='" + actualWorkPosition + '\'' +
            ", 6 - phoneNumber='" + phoneNumber + '\'' +
            ", 7 - timezone='" + timezone + '\'' +
            ", 8 - profilePicture=" + profilePicture +
            9-exit
        */
            choice = Keyboard.readInt("choice");
            // we need if no switch because it will be changed to enum
            if(choice == 1){
                String newUsername = Keyboard.readString("username");
                currentProfile.setUsername(newUsername);
                DAOProfile.update(currentProfile);

                System.out.println("Username changed !");
            }
            if(choice == 2){
                String newCurrentStatus = Keyboard.readString("currentStatus");
                currentProfile.setCurrentStatus(newCurrentStatus);
                System.out.println("Status changed !");
            }
            if(choice == 3){
                String newCompleteName = Keyboard.readString("completeName");
                currentProfile.setCompleteName(newCompleteName);
                System.out.println("Complete name changed !");
            }
            if(choice == 4){
                String newShownName = Keyboard.readString("shownName");
                currentProfile.setShownName(newShownName);
                System.out.println("Shown name changed !");
            }
            if(choice == 5){
                String newActualWorkPosition = Keyboard.readString("actualWorkPosition");
                currentProfile.setActualWorkPosition(newActualWorkPosition);
                System.out.println("Actual position changed !");
            }
            if(choice == 6){
                String newPhoneNumber = Keyboard.readString("phoneNumber");
                currentProfile.setPhoneNumber(newPhoneNumber);
                System.out.println("Phone number changed !");
            }
            if(choice == 7){
                String newTimezone = Keyboard.readString("timezone");
                currentProfile.setTimezone(newTimezone);
                System.out.println("Timezone changed !");
            }
            // need save file - for now it's a String if we want
            //if(choice == 8){
            //  File newProfilePicture = (File) readString("profilePicture");
            // currentProfile.setProfilePicture(newProfilePicture);
            // System.out.println("Profile picture changed! ");
            //}

            // add Skype ?
        }while(choice != 9);
    }


    public void addCollaborator(){
        Profile user = user.getCurrentProfile();
        Workspace currentWorkspace = user.getWorkspace();
        ArrayList<Profile> members = currentWorkspace.getMemberProfiles();

        ArrayList<Profile> collaborators = user.getCollaborators();
        String collaboratorShownName = Keyboard.readString("name of the collaborator");
        for(Profile collaborator : collaborators){
            if(collaborator.getShownName() == collaboratorShownName){
                System.out.println("Collaborator already exists");
                return;
            }
            for(Profile member : members){
                ArrayList<Profile> memberProfiles = member.getProfiles();
                for(Profile memberProfile : memberProfiles){
                    if (memberProfile.getShownName() == collaboratorShownName) {
                        System.out.println("Adding collaborator...");
                        user.getCollaborators().add(memberProfile);
                        System.out.println("Collaborator added");
                        return;
                    }
                }
            }
        }
        System.out.println("Collaborator does not exist in the workspace");
    }

    public void visitCollaboratorProfile(){
        Profile user = user.getCurrentProfile();
        Workspace currentWorkspace = user.getWorkspace();
        ArrayList<Profile> members = currentWorkspace.getMemberProfiles();

        ArrayList<Profile> collaborators = user.getCollaborators();
        String collaboratorShownName = Keyboard.readString("name of the collaborator");
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
        Profile user = user.getCurrentProfile();
        Workspace currentWorkspace = user.getWorkspace();
        ArrayList<Profile> members = currentWorkspace.getMemberProfiles();

        ArrayList<Profile> collaborators = user.getCollaborators();
        String collaboratorShownName = Keyboard.readString("name of the collaborator");
        for(Profile collaborator : collaborators){
            if(collaborator.getShownName() == collaboratorShownName){
                System.out.println("deleting collaborator...");
                user.getCollaborators().remove(collaborator);
                System.out.println("Collaborator deleted");
                return;
            }
        }
        System.out.println("Collaborator does not exist");
    }

}
