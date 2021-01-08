package service.user;

import database.SQLMessageDAO;
import model.communication.Conversation;
import model.communication.Message;
import model.communication.Workspace;
import model.communication.WorkspaceChannel;
import model.user.Profile;
import model.user.User;
import service.AbstractServiceDAO;
import tool.DataManipulator;
import tool.Keyboard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * UserServiceDAO : provide services related to the User
 */
public class UserServiceDAO extends AbstractServiceDAO {

    /** OK
     *
     * @param inputedEmail entered email
     * @param inputedPassword entered password
     * @param confirmedInputedPassword entered password
     * @return user if registrated, null if not
     * @throws SQLException
     */
    public static User register(String inputedEmail, String inputedPassword, String confirmedInputedPassword) throws SQLException {
            System.out.println("Proceeding to registration...");

            if (!(DataManipulator.verifyInputedEmail(inputedEmail) | DataManipulator.verifyInputedPassword(inputedPassword) | DataManipulator.verifyConfirmedPassword(inputedPassword, confirmedInputedPassword))) {
                return null;
            }

            System.out.println("You entered " + inputedEmail + ".");
            System.out.println("You entered the password: " + inputedPassword);


            for (User user : (ArrayList<User>) DAOUser.selectAll()){
                if (user.getId().equalsIgnoreCase(inputedEmail)){
                    System.out.println("User already registered ! Please connect to your account");
                     return null; // failed to register
                }
            }

            System.out.println("Proceeding to registration");

            // add the user to the database
            if (DAOUser.insert(new User(inputedEmail, inputedPassword)) != null){
                System.out.println("user successfully registered!");
                return DAOUser.select(inputedEmail);
            }

            // failed to register
            System.out.println("Failed to register the user. Please retry.");
            return null;
        }

    /** OK
     *
     * @param inputedEmail inputedEmail
     * @param inputedPassword inputedPassword
     * @return user if signed in, null if not
     * @throws SQLException
     */

    public static User login(String inputedEmail, String inputedPassword) throws SQLException {


        if (!(DataManipulator.verifyInputedEmail(inputedEmail) | DataManipulator.verifyInputedPassword(inputedPassword) )) {
            return null;
        }

        System.out.println("You entered " + inputedEmail + " .");
        System.out.println("You entered " + inputedPassword + " .");

        User userToRegister = null;
        for (User user : DAOUser.selectAll()) {
            if (user.getId().equalsIgnoreCase(inputedEmail)) {
                userToRegister = user;
                break;
            }
        }

        if (userToRegister == null) {
            System.out.println("Unexisting user, not connected");
            return null;
        }

        if(DAOUser.signIn(userToRegister) != null){
            System.out.println("Congrates you are now logged in");
            return userToRegister;
        }

        System.out.println("Error, you can not log in");
        return null;
    }

    // you can visit a profile only when you are in the workspace
    public void visitProfile() throws SQLException {
        String keyPressed = "";
        Scanner scannerKeyPressed = null;

        if (currentConnectedUser == null) {
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
            System.out.println("User does not have a profile in the current workspace !");
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
        User connectedUser = DAOUser.select(currentConnectedUser.getId());

        if (connectedUser == null) {
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
        DAOProfile.update(DAOProfile.select(currentProfile.getId()));
    }

    public void editAccount(){
        String newEmailAddress = "";
        String newPasswordConfirm = "";
        String newPassword = "";
        boolean passwordConfirmed = false;
        String currentPassword = "";
        User currentUser = getConnectedUser();

        System.out.println("Edit your account");

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
        User connectedUser = slackSystem.getConnectedUser();
        Workspace connectedWorkspace = connectedUser.getCurrentWorkspace();
        ArrayList<User> admins = connectedWorkspace.getAdminProfiles();
        if (!admins.contains(connectedUser)) {
            System.out.println("Action not authorized ! The user is not admin");
            return;
        }
        // admin
        ArrayList<User> members = connectedWorkspace.getMemberProfiles();
        String memberId = readString("member's email to ban");
        for(User member : members){
            if (member.getEmail() == memberId) {
                System.out.println("Banning member...");
                connectedWorkspace.getMemberProfiles().remove(member);
                if(admins.contains(member)) {
                    connectedWorkspace.getAdminProfiles().remove(member);
                }
                connectedWorkspace.getBannedProfiles().add(member);
                System.out.println("Member banned");
                return;
            }
        }
        System.out.println("Member not found");
    }

    public void kickMemberFromWorkspace(){
        User connectedUser = slackSystem.getConnectedUser();
        Workspace connectedWorkspace = connectedUser.getCurrentWorkspace();
        ArrayList<User> admins = connectedWorkspace.getAdminProfiles();
        if (!admins.contains(connectedUser)) {
            System.out.println("Action not authorized ! The user is not admin");
            return;
        }
        // admin
        ArrayList<User> members = connectedWorkspace.getMemberProfiles();
        String memberId = readString("member's email to kick");
        for(User member : members){
            if (member.getEmail() == memberId) {
                System.out.println("kicking member...");
                connectedWorkspace.getMemberProfiles().remove(member);
                if(admins.contains(member)) {
                    connectedWorkspace.getAdminProfiles().remove(member);
                }
                System.out.println("Member kicked");
                return;
            }
        }
        System.out.println("Member not found");
    }

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
    }

    //function called by a user
    public Workspace createWs() throws SQLException {
        Workspace workspace,ws;

        String workspaceName;
        Scanner scanner;

        do {
            System.out.println("Enter the name of the workspace");
            scanner = new Scanner(System.in);
            workspaceName = scanner.nextLine();
            ws = DAOWorkspace.select(workspaceName);
            if(ws != null){
                System.out.println("This name already exist please enter another one");
            }
        } while(ws != null);

        workspace = new Workspace(workspaceName);

        ws = DAOWorkspace.insert(workspace);

        if(ws!=null){
            System.out.println("this workspace has been created succefully");
        }else{
            System.out.println("this workspace hasn't been created ! please try again");
        }
        //create a profile for the user who's creating the workspace
        Profile profile;
        profile = createProfile(currentConnectedUser.getId(),workspace.getId());

        //put the creator as an admin
        profile.setIsAdminWS(1);
        DAOProfile.update(profile);

        return workspace;
    }

    //function called by a profile
    public WorkspaceChannel createCh(Workspace workspace) throws SQLException {
        WorkspaceChannel channel,ch;
        String chName;
        Scanner buffer;

        System.out.println("Enter the name of the channel");
        do{
            buffer = new Scanner(System.in);
            chName = buffer.nextLine();
            ch = DAOChannel.select(chName);
            if(ch != null) {
                System.out.println("this channel name already exist, please choose another name");
            }
        }while(ch != null);
        channel = new WorkspaceChannel(chName);
        channel.setWsId(workspace.getId());

        //putting the profile that created it as an admin (to change !)
        String id = currentConnectedUser.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(id);
        profile.setIsAdminCh(1);
        DAOProfile.update(profile);

        //choose if you want it to be private or not
        int choice;
        System.out.println("do you want this channel to be private ?");
        System.out.println("1- yes");
        System.out.println("0- No");
        buffer = new Scanner(System.in);
        choice = buffer.nextInt();
        if(choice == 1) {
            channel.setPrivate(1);
            System.out.println("this created channel is private");
        }else{
            channel.setPrivate(0);
        }

        ch = DAOChannel.insert(channel);
        if(ch!=null){
            System.out.println("this channel has been created succefully");
        }else{
            System.out.println("this channel hasn't been created ! please try again");
        }

        return channel;
    }

    //called by a user
    public void quitWs(Workspace workspace) throws SQLException {
        String id = currentConnectedUser.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(id);
        DAOProfile.delete(profile);
    }

    //called by a profile
    public void quitCh(WorkspaceChannel channel){
        //we actually can't quit a channel
    }

    //called by a user
    public void deleteWs(Workspace workspace) throws SQLException {
        ArrayList<WorkspaceChannel> wsChannel = new ArrayList<WorkspaceChannel>();
        ArrayList<Profile> wsProfiles = new ArrayList<Profile>();

        String idProfile = currentConnectedUser.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(idProfile);

        if( profile.isAdminWS() == 1 ){
            //delete all channels of this workspace
            wsChannel = (ArrayList<WorkspaceChannel>) DAOChannel.selectAll();
            for(WorkspaceChannel channel : wsChannel){
                if(channel.getWsId()==workspace.getId()){
                    DAOChannel.delete(channel);
                }
            }
            wsProfiles = (ArrayList<Profile>) DAOProfile.selectAll();

            //delete all its profiles
            for(Profile p : wsProfiles){
                if(p.getWorkspaceId()==workspace.getId()){
                    DAOProfile.delete(p);
                }
            }

            //delete the workspace
            DAOWorkspace.delete(workspace);
            System.out.println("this workspace has been deleted successfully");

        }else{
            System.out.println("you don't have any right on this workspace");
        }

    }

    //called by a profile
    public void deleteCh(WorkspaceChannel channel){
        ArrayList<Profile> chProfiles = new ArrayList<Profile>();

        if(connectedProfile.getIsAdminCh() == 0){
            System.out.println("you don't have any right on this channel");
        }else {
            //take the role of admin from the profiles that are admins on this channel
            chProfiles = (ArrayList<Profile>) DAOProfile.selectAll();
            for(Profile profile : chProfiles){
                if(profile.isAdminCh()==1){
                    profile.setIsAdminCh(0);
                }
            }
            //delete the channel
            DAOChannel.delete(channel);
            System.out.println("this channel has been deleted successfully");
        }
    }

    public void editWs(Workspace workspace) throws SQLException {//called by a user
        Workspace ws;
        ArrayList<Profile> wsProfiles = new ArrayList<Profile>();
        ArrayList<WorkspaceChannel> wsChannels = new ArrayList<WorkspaceChannel>();
        String newName;
        boolean exist = false;
        String idProfile = currentConnectedUser.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(idProfile);

        Scanner buff;
        if(profile.isAdminWS()==0){
            System.out.println("you don't have any right on this workspace");
        }else{
            System.out.println("Enter the new name of this workspace");
            do {
                buff = new Scanner(System.in);
                newName = buff.nextLine();
                ws = DAOWorkspace.select(newName);
                if(ws != null){
                    exist = true;
                    System.out.println("This name is taken, please choose another one");
                }
            }while(exist);
            //change the id workspace for all the channels
            wsChannels = (ArrayList<WorkspaceChannel>) DAOChannel.selectAll();
            for(WorkspaceChannel channel: wsChannels){
                if(channel.getWsId()==workspace.getId()){
                    channel.setWsId(newName);
                    DAOChannel.update(channel);
                }
            }
            //change the id workspace for all the profiles
            wsProfiles = (ArrayList<Profile>) DAOProfile.selectAll();
            for(Profile p: wsProfiles){
                if(p.getWorkspaceId()==workspace.getId()){
                    p.setWorkspaceId(newName);
                    DAOProfile.update(p);
                }
            }
            //update the workspace
            workspace.setName(newName);
            ws = DAOWorkspace.update(workspace);
            System.out.println("The workspace informations has been changed successfully");
        }
    }


    public void editCh(WorkspaceChannel channel) throws SQLException {//called by a profile
        String newName;
        Scanner buff;
        WorkspaceChannel wsChannel;

        boolean exist = false;
        if(connectedProfile.getIsAdminCh()==0){
            System.out.println("you don't have any right on this channel");
        }else{
            System.out.println("Enter the new name of this channel");
            do {
                buff = new Scanner(System.in);
                newName = buff.nextLine();
                wsChannel = DAOChannel.select(newName);
                if(channel!=null) {
                    exist=true;
                    System.out.println("this channel name already exist, please choose another one");
                }

            }while(exist);
            //updating the id channel for all the messages
            channel.setName(newName);
            wsChannel = DAOChannel.update(channel);
            System.out.println("The channel informations has been changed successfully");

        }
    }

    public Message sendChannelMsg(WorkspaceChannel channel){//called by a profile
        Message message;
        String content;
        Scanner buffer;
        Date date = new Date();

        System.out.println("Enter the content of your message");
        buffer = new Scanner(System.in);
        content = buffer.next();
        message = new Message(connectedProfile,content);
        message.setCreatedAt(date);
        message.setIdCh(channel.getId());
        //channel.getConversation().add(message);
        return DAOMessageChannel.insert(message);
    }

    public void deleteChannelMsg(Message msg){//called by a profile
        if(msg.getIdSenderMessage()== connectedProfile.getId()){
            DAOMessageChannel.delete(msg);
            System.out.println("the message has been deleted succefully");
        }else{
            System.out.println("The message that you are trying to delete is not yours, please select a message that has been sent by you");
        }
    }

    public Message editChannelMsg(Message msg){
        Message message;
        String newContent;
        Scanner buffer;
        Date date = new Date();
        System.out.println("Enter the new content of your message");
        buffer = new Scanner(System.in);
        newContent = buffer.next();
        msg.setCreatedAt(date);

        msg.setContent(newContent);
        message = DAOMessageChannel.update(msg);
        System.out.println("your content has been changed successfully");
        return message;
    }
    public Conversation createConversation(){
        Conversation conversation;
        String name;
        Scanner buffer;
        Date date = new Date();
        buffer = new Scanner(System.in);
        name = buffer.nextLine();
        conversation = new Conversation(name,date);
        return conversation;
    }
    public Message sendConversationMsg(Conversation conversation){
        Message message;
        String content;
        Scanner buffer;
        Date date = new Date();

        System.out.println("Enter the content of your message");
        buffer = new Scanner(System.in);
        content = buffer.next();
        message = new Message(connectedProfile,content);
        message.setCreatedAt(date);
        message.setIdConversation(conversation.getId());
        return DAOMessageDirect.insert(message);
    }
    public Message editConversationMsg(Message msg){
        Message message;
        String newContent;
        Scanner buffer;
        Date date = new Date();
        System.out.println("Enter the new content of your message");
        buffer = new Scanner(System.in);
        newContent = buffer.next();
        msg.setCreatedAt(date);

        msg.setContent(newContent);
        message = DAOMessageDirect.update(msg);
        System.out.println("your content has been changed successfully");
        return message;
    }

    //called to add a collaborator
    public void addWsCollaborator(User collab,Workspace workspace) {
        Profile profileCollab = createProfile(collab.getId(),workspace.getId());
        DAOProfile.insert(profileCollab);
    }

    //called by a user to add a collaborator
    public void addChCollaborator(Profile collab) {
        //How to add a collab in a channel ?
        //How can we know that a profile /user is in a "X" channel
    }

    //called by a user
    public Profile createProfile(String idUsr,String idWs){
        Profile profile = new Profile(idWs,idUsr);
        String id = idUsr+"."+idWs;
        profile.setId(id);
        return profile;
    }

}
