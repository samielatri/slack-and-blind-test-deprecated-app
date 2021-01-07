package model.service;

import model.entity.SlackSystem;
import model.entity.group.Workspace;
import model.entity.user.Profile;
import model.entity.user.User;

import java.util.ArrayList;
import java.util.Scanner;

public class SlackSystemService {

    // new code -> not factored because we consider that there is no user and everything is called from static context
    // in order to see if it works, if it does we factor the code
    // no service -> problem

    // TO DO in tool class:
        // readString(String_to_describe_what_to_enter, boolean_not_valid_entry, String_to_notify_the_error)
            // example : readString("e-mail address", !validInputEmail, "Please verify the entered email address:")
        // same for readInt(..,..,..)
        // same for readDouble(..,..,..)


    /**
     * @param email
     * @return true if the email is valid, false if not
     */
    public boolean isValidEmailAddress(String email){
        // debug print
        System.out.println("Verifying the email address...");
        // using regex pattern and matchers to verify the email address
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher matcher = pattern.matcher(email);

        // debug print
        System.out.println("End of verification of the email address.");

        return matcher.matches();
    }

    public void register(){
        // debug print
        System.out.println("Proceeding to registration...");
        SlackSystem slackSystem = new SlackSystem();
        Scanner scannerEmail; // scanner for email
        Scanner scannerPassword; // scanner for password
        boolean validInputEmail = false; // true if the imputed email is valid, false if not
        String imputedEmail = ""; // email imputed from the user
        String imputPassword = ""; // imputed password from the user
        User connectUser = null; // connected user




        // get valid imputed email from the user
        do {
            System.out.println("Please enter your e-mail : ");
            scannerEmail = new Scanner(System.in);
            System.out.print("> ");
            imputedEmail = scannerEmail.nextLine();
            validInputEmail = isValidEmailAddress(imputedEmail);
            if (!validInputEmail) {
                System.out.println("Please verify the entered email address!");
            }
        }while(!validInputEmail); // refactor it to readString("e-mail address", !validInputEmail, "Please verify the entered email address:")

        System.out.println("You entered " + imputedEmail + " .");

        System.out.println("Please enter your password : ");
        scannerPassword = new Scanner(System.in);
        System.out.print("> ");
        imputPassword = scannerPassword.nextLine();


        for (User tempUser : slackSystem.getUsers()){
            if (tempUser.getEmail() == imputedEmail){
                System.out.println("User already registered ! Please connect to your account");
                return;
            }
        }

        User user = new User(imputedEmail, imputPassword);

        System.out.println("Registring...");

        slackSystem.getUsers().add(user);

        if(slackSystem.getUsers().contains(user)){
            System.out.println("user registrated!");
        } else {
            System.out.println("failed to register the user. Please retry.");
        }
    }

    public void connect(){
        System.out.println("Conncetion");
        SlackSystem slackSystem = new SlackSystem();
        Scanner scannerEmail;
        Scanner scannerPassword;
        boolean validInputEmail = false;
        String inputEmail = "";
        String inputPassword = "";
        User connectUser = null;

        do {
            System.out.println("Please enter your e-mail : ");
            scannerEmail = new Scanner(System.in);
            System.out.print("> ");
            inputEmail = scannerEmail.nextLine();
            validInputEmail = isValidEmailAddress(inputEmail);
            if (!validInputEmail) {
                System.out.println("Please verify the entered email address!");
            } else {
                System.out.println("You entered " + inputEmail + " .");
            }
        }while(validInputEmail);

        System.out.println("Please enter your password : ");
        scannerPassword = new Scanner(System.in);
        System.out.print("> ");
        inputPassword = scannerPassword.nextLine();

        User user = new User(inputEmail, inputPassword);

        if ( slackSystem.getConnectedUser() != null) {
            if (slackSystem.getConnectedUser().getEmail() == inputEmail) {
                System.out.println("User already conncted !");
            } else {
                System.out.println("A user is already connected ! Please disconnect before connecting to a new user");
            }
            return ;
        }

        for (User tempUser : slackSystem.getUsers()){
            if (tempUser.getEmail() == inputEmail){
                if(tempUser.getPassword() == inputPassword){
                    connectUser = tempUser;
                    break;
                } else {
                    System.out.println("Wrong password");
                    break;
                }
            }
        }

        if (connectUser == null) {
            System.out.println("User does not exist !");
            System.out.println("Please verify your entered informations or create an account if you don't have one.");
            return ;
        }

        System.out.println("Connecting...");
        slackSystem.setConnectedUser(user);

        if (slackSystem.getConnectedUser() == user) {
            System.out.println("User connected successfully !");
        } else {
            System.out.println("Connection failed ! Please retry.");
        }
    }

    // you can vist a profile only when you are in the workspace
    public void visitProfile(){
        String keypressed = "";
        Scanner scannerKeypressed = null;
        SlackSystem slackSystem = new SlackSystem();
        User connectedUser = slackSystem.getConnectedUser();

        if (connectedUser == null) {
            System.out.println("No user connected !");
            return ;
        }

        Workspace currentWorkspace = connectedUser.getCurrentWorkspace();
        if (currentWorkspace == null){
            System.out.println("User not connected to any workspace !");
            return ;
        }

        Profile currentProfile = connectedUser.getCurrentProfile();
        if (currentProfile == null){
            System.out.println("User does not have a profile in the current workspace !");
            return ;
        }

        System.out.println(currentProfile.toString());
        System.out.println("Press any key to come back");
        System.out.print("> ");
        while(keypressed == "" || keypressed == null) {
            scannerKeypressed = new Scanner(System.in);
            keypressed = scannerKeypressed.nextLine();
        }
    }

    public void updateProfile(){
        String keypressed = "";
        Scanner scannerKeypressed = null;
        SlackSystem slackSystem = new SlackSystem();
        User connectedUser = slackSystem.getConnectedUser();

        if (connectedUser == null) {
            System.out.println("No user connected !");
            return ;
        }

        Workspace currentWorkspace = connectedUser.getCurrentWorkspace();
        if (currentWorkspace == null){
            System.out.println("User not connected to any workspace !");
            return ;
        }

        Profile currentProfile = connectedUser.getCurrentProfile();
        if (currentProfile == null){
            System.out.println("User does not have a profile in the current workspace !");
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
            choice = readInt("choice");
            // we need if no switch because it will be changed to enum
            if(choice == 1){
                String newUsername = readString("username");
                currentProfile.setUsername(newUsername);
                System.out.println("Username changed !");
            }
            if(choice == 2){
                String newCurrentStatus = readString("currentStatus");
                currentProfile.setCurrentStatus(newCurrentStatus);
                System.out.println("Status changed !");
            }
            if(choice == 3){
                String newCompleteName = readString("completeName");
                currentProfile.setCompleteName(newCompleteName);
                System.out.println("Complete name changed !");
            }
            if(choice == 4){
                String newShownName = readString("shownName");
                currentProfile.setShownName(newShownName);
                System.out.println("Shown name changed !");
            }
            if(choice == 5){
                String newActualWorkPosition = readString("actualWorkPosition");
                currentProfile.setActualWorkPosition(newActualWorkPosition);
                System.out.println("Actual position changed !");
            }
            if(choice == 6){
                String newPhoneNumber = readString("phoneNumber");
                currentProfile.setPhoneNumber(newPhoneNumber);
                System.out.println("Phone number changed !");
            }
            if(choice == 7){
                String newTimezone = readString("timezone");
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
    // need to use keyboardInput in future
    private int readInt(String printable) {
        System.out.println("Please enter " + printable + ": > ");
        Scanner scanner = new Scanner(System.in);
        int intInput = scanner.nextInt();
        return intInput;
    }

    private String readString(String printable) {
        System.out.println("Please enter " + printable + ": > ");
        Scanner scanner = new Scanner(System.in);
        String stringInput = scanner.nextLine();
        return stringInput;
    }

    public void editAccount(){
        String newEmailAddress = "";
        String newPasswordConfirm = "";
        String newPassword = "";
        boolean passwordConfirmed = false;
        String currentPassword = "";
        SlackSystem slackSystem = new SlackSystem();
        User currentUser = slackSystem.getConnectedUser();

        System.out.println("Edit your acoount");

        if (currentUser == null) {
            System.out.println("No user connected !");
            return ;
        }

        int intInput = 0;
        do {
            System.out.println("edit :\n" + "\t1- email address\n\t2- password\n\t 3-return");
            intInput = readInt("your choice");
        } while (intInput > 0 && intInput < 3);
        if (intInput == 1){
            do {
                newEmailAddress = readString("new email address");
                currentUser.setEmail(newEmailAddress);
                System.out.println("Email Address changed successfully");
            }while(! isValidEmailAddress(newEmailAddress));
        }
        if (intInput== 2) {
            currentPassword = readString("current password");
            if (currentPassword == currentUser.getPassword()) {
                do {
                    newPassword = readString("new password");
                    newPasswordConfirm = readString("new password confirmation");
                    passwordConfirmed = (newPassword == newPasswordConfirm);
                    if (passwordConfirmed) {
                        currentUser.setPassword(newPassword);
                        System.out.println("Password changed successfully");
                    }
                } while (!passwordConfirmed);
            } else {
                System.out.println("Password does not match");
                // return
            }
        }
        // 3
        // return
    }

    public void disconnect(){
        System.out.println("disconnect");
        SlackSystem slackSystem = new SlackSystem();
        if (slackSystem.getConnectedUser() == null){
            System.out.println("no connected user");
            return;
        }
        System.out.println("disconnecting...");
        slackSystem.setConnectedUser(null);
        System.out.println("disconnected successfully");
    }

    public void deleteAccount(){
        System.out.println("delete account");
        SlackSystem slackSystem = new SlackSystem();
        User user = slackSystem.getConnectedUser();
        if (user == null){
            System.out.println("no user connected");
            return;
        }
        disconnect();
        System.out.println("Deleting account...");
        ArrayList<User> usersList = slackSystem.getUsers();
        if(usersList.contains(user)) {
            usersList.remove(user);
            slackSystem.setUsers(usersList);
            System.out.println("Account deleted");
        } else {
            System.out.println("The user was never registred... /!\\");
        }
    }

    public void connectWorkspace(){
        SlackSystem slackSystem = new SlackSystem();
        User user = slackSystem.getConnectedUser();
        ArrayList<Workspace> listWorskspace = user.getWorkspaces();
        String workspaceId = readString("workspace id to join");
        for(Workspace tempWorkspace : listWorskspace){
            String tempWorkspaceId = tempWorkspace.getId();
            if (workspaceId == tempWorkspaceId){
                System.out.println("Connectiong to workspace ...");
                user.setCurrentWorkspace(tempWorkspace);
                System.out.println("Connected to the workspace successfully !");
                return ;
            }
        }
        // didn't find and didn't join worskapce
        System.out.println("Workspace is not joined");
    }

    public void joinWorkspace(){
        SlackSystem slackSystem = new SlackSystem();
        User user = slackSystem.getConnectedUser();
        ArrayList<Workspace> listWorkspace = slackSystem.getWorkspaces();
        ArrayList<Workspace> userWorkspace = user.getWorkspaces();
        String workspaceId = readString("workspace id to join");

        // check in all workspaces if the workspace exists
        for(Workspace tempWorkspace : listWorkspace){
            String tempWorkspaceId = tempWorkspace.getId();
            if (workspaceId == tempWorkspaceId){
                for(Workspace tempUserWorkspace : userWorkspace){
                    String tempUserWorkspaceId = tempUserWorkspace.getId();
                    if(tempUserWorkspaceId == tempWorkspaceId){
                        System.out.println("Workpace already joined");
                        return ;
                    } else {
                        System.out.println("Joining workspace ...");
                        userWorkspace.add(tempWorkspace);
                        user.setWorkspaces(userWorkspace);
                        System.out.println("workspace joined !");
                        return ;
                    }
                }
            }
        }
        // didn't find and didn't join worskapce
        System.out.println("Workspace does not even exist, the user cannot have been joining it and cannot join it either.");
    }

    public void banMemberFromWorkspace(){
        SlackSystem slackSystem = new SlackSystem();
        User connectedUser = slackSystem.getConnectedUser();
        Workspace connectedWorkspace = connectedUser.getCurrentWorkspace();
        ArrayList<User> admins = connectedWorkspace.getAdmins();
        if (!admins.contains(connectedUser)) {
            System.out.println("Action not authorized ! The user is not admin");
            return;
        }
        // admin
        ArrayList<User> members = connectedWorkspace.getMembers();
        String memberId = readString("member's email to ban");
        for(User member : members){
            if (member.getEmail() == memberId) {
                System.out.println("Banning member...");
                connectedWorkspace.getMembers().remove(member);
                if(admins.contains(member)) {
                    connectedWorkspace.getAdmins().remove(member);
                }
                connectedWorkspace.getBannedUsers().add(member);
                System.out.println("Member banned");
                return;
            }
        }
        System.out.println("Member not found");
    }

    public void kickMemberFromWorkspace(){
        SlackSystem slackSystem = new SlackSystem();
        User connectedUser = slackSystem.getConnectedUser();
        Workspace connectedWorkspace = connectedUser.getCurrentWorkspace();
        ArrayList<User> admins = connectedWorkspace.getAdmins();
        if (!admins.contains(connectedUser)) {
            System.out.println("Action not authorized ! The user is not admin");
            return;
        }
        // admin
        ArrayList<User> members = connectedWorkspace.getMembers();
        String memberId = readString("member's email to kick");
        for(User member : members){
            if (member.getEmail() == memberId) {
                System.out.println("kicking member...");
                connectedWorkspace.getMembers().remove(member);
                if(admins.contains(member)) {
                    connectedWorkspace.getAdmins().remove(member);
                }
                System.out.println("Member kicked");
                return;
            }
        }
        System.out.println("Member not found");
    }

    public void addCollaborator(){
        SlackSystem slackSystem = new SlackSystem();
        User user = slackSystem.getConnectedUser();
        Profile profile = user.getCurrentProfile();
        Workspace currentWorkspace = profile.getWorkspace();
        ArrayList<User> members = currentWorkspace.getMembers();

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
        SlackSystem slackSystem = new SlackSystem();
        User user = slackSystem.getConnectedUser();
        Profile profile = user.getCurrentProfile();
        Workspace currentWorkspace = profile.getWorkspace();
        ArrayList<User> members = currentWorkspace.getMembers();

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
        SlackSystem slackSystem = new SlackSystem();
        User user = slackSystem.getConnectedUser();
        Profile profile = user.getCurrentProfile();
        Workspace currentWorkspace = profile.getWorkspace();
        ArrayList<User> members = currentWorkspace.getMembers();

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
    }

}
