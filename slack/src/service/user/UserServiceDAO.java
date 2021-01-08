package service.user;

import model.communication.Conversation;
import model.communication.Message;
import model.communication.Workspace;
import model.communication.WorkspaceChannel;

import model.user.Profile;

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

    /**
     * Can register only if no User is connected.
     * @return the User that was registered, null if not registered
     * @throws SQLException
     */
    public Profile register() throws SQLException {
        System.out.println("Proceeding to registration...");
        Scanner scannerPassword; // scanner for password
        boolean validInputEmail = false; // true if the imputed email is valid, false if not
        String inputtedEmail = ""; // email imputed from the user
        String inputtedPassword = ""; // imputed password from the user

        // get valid imputed email from the user
        do {
            inputtedEmail = Keyboard.readString("e-mail");
            validInputEmail = DataManipulator.isValidEmailAddress(inputtedEmail);
            if (!validInputEmail) {
                System.out.println("Please verify the entered email address!");
            }
        }while(!validInputEmail);

        System.out.println("You entered " + inputtedEmail + ".");

        System.out.println("Please enter your password : ");
        scannerPassword = new Scanner(System.in);
        System.out.print("> ");
        inputtedPassword = scannerPassword.nextLine();

        for (Profile profile : DAOUser.selectAll()){
            if (profile.getEmail().equalsIgnoreCase(inputtedEmail)){
                System.out.println("User already registered ! Please connect to your account");
                 return null; // failed to register
            }
        }

        System.out.println("Registering...");

        // add the user to the database
        if (DAOUser.insert(new Profile(inputtedEmail, inputtedPassword)) != null){
            System.out.println("user successfully registered!");
            return DAOUser.select(inputtedEmail);
        }

        // failed to register
        System.out.println("failed to register the user. Please retry.");
        return null;
    }

    public void connect() throws SQLException {
        System.out.println("connection...");
        Scanner scannerEmail;
        Scanner scannerPassword;
        boolean validInputEmail = false;
        String inputEmail = "";
        String inputPassword = "";
        String inputPasswordConfirmation = "";
        Profile connectProfile = null;

        do {
            System.out.println("Please enter your e-mail : ");
            scannerEmail = new Scanner(System.in);
            System.out.print("> ");
            inputEmail = scannerEmail.nextLine();
            validInputEmail = DataManipulator.isValidEmailAddress(inputEmail);
            if (!validInputEmail) {
                System.out.println("Please verify the entered email address!");
            } else {
                System.out.println("You entered " + inputEmail + " .");
            }
        }while(!validInputEmail);

        do {
            System.out.println("Please enter your password : ");
            scannerPassword = new Scanner(System.in);
            System.out.print("> ");
            inputPassword = scannerPassword.nextLine();
            System.out.println("Please confirm your password : ");
            Scanner scannerPasswordConfirmation = new Scanner(System.in);
            System.out.print("> ");
            inputPasswordConfirmation = scannerPassword.nextLine();
        } while(!inputPassword.equals(inputPasswordConfirmation));
        Profile profile = new Profile(inputEmail, inputPassword);

        if ( currentConnectedProfile != null) {
            if (currentConnectedProfile.getEmail().equalsIgnoreCase(inputEmail)) {
                System.out.println("User already connected !");
            } else {
                System.out.println("A user is already connected ! Please disconnect before connecting to a new user");
            }
            return ;
        }

        for (Profile tempProfile : (ArrayList<Profile>) DAOUser.selectAll()){
            if (tempProfile.getEmail().equalsIgnoreCase(inputEmail)){
                if(tempProfile.getPassword().equals(inputPassword)){
                    connectProfile = tempProfile;
                    break;
                } else {
                    System.out.println("Wrong password");
                    break;
                }
            }
        }

        if (connectProfile == null) {
            System.out.println("User does not exist !");
            System.out.println("Please verify your entered information or create an account if you don't have one.");
            return ;
        }

        System.out.println("Connecting...");
        currentConnectedProfile = DAOUser.select(profile.getId());

        // signIn

        if (connectProfile == profile) {
            System.out.println("User connected successfully !");
        } else {
            System.out.println("Connection failed ! Please retry.");
        }
    }

    public void editAccount(){
        String newEmailAddress = "";
        String newPasswordConfirm = "";
        String newPassword = "";
        boolean passwordConfirmed = false;
        String currentPassword = "";
        Profile currentProfile = slackSystem.getConnectedUser();

        System.out.println("Edit your acoount");

        if (currentProfile == null) {
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
                currentProfile.setEmail(newEmailAddress);
                System.out.println("Email Address changed successfully");
            }while(! isValidEmailAddress(newEmailAddress));
        }
        if (intInput== 2) {
            currentPassword = readString("current password");
            if (currentPassword == currentProfile.getPassword()) {
                do {
                    newPassword = readString("new password");
                    newPasswordConfirm = readString("new password confirmation");
                    passwordConfirmed = (newPassword == newPasswordConfirm);
                    if (passwordConfirmed) {
                        currentProfile.setPassword(newPassword);
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
        Profile profile = slackSystem.getConnectedUser();
        if (profile == null){
            System.out.println("no user connected");
            return;
        }
        disconnect();
        System.out.println("Deleting account...");
        ArrayList<Profile> usersList = slackSystem.getUsers();
        if(usersList.contains(profile)) {
            usersList.remove(profile);
            slackSystem.setUsers(usersList);
            System.out.println("Account deleted");
        } else {
            System.out.println("The user was never registred... /!\\");
        }
    }

    public void connectWorkspace(){
        Profile profile = slackSystem.getConnectedUser();
        ArrayList<Workspace> listWorskspace = profile.getWorkspaces();
        String workspaceId = readString("workspace id to join");
        for(Workspace tempWorkspace : listWorskspace){
            String tempWorkspaceId = tempWorkspace.getId();
            if (workspaceId == tempWorkspaceId){
                System.out.println("Connectiong to workspace ...");
                profile.setCurrentWorkspace(tempWorkspace);
                System.out.println("Connected to the workspace successfully !");
                return ;
            }
        }
        // didn't find and didn't join worskapce
        System.out.println("Workspace is not joined");
    }

    public void joinWorkspace(){
        Profile profile = slackSystem.getConnectedUser();
        ArrayList<Workspace> listWorkspace = slackSystem.getWorkspaces();
        ArrayList<Workspace> userWorkspace = profile.getWorkspaces();
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
                        profile.setWorkspaces(userWorkspace);
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
        Profile connectedProfile = slackSystem.getConnectedUser();
        Workspace connectedWorkspace = connectedProfile.getCurrentWorkspace();
        ArrayList<Profile> admins = connectedWorkspace.getAdminProfiles();
        if (!admins.contains(connectedProfile)) {
            System.out.println("Action not authorized ! The user is not admin");
            return;
        }
        // admin
        ArrayList<Profile> members = connectedWorkspace.getMemberProfiles();
        String memberId = readString("member's email to ban");
        for(Profile member : members){
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
        Profile connectedProfile = slackSystem.getConnectedUser();
        Workspace connectedWorkspace = connectedProfile.getCurrentWorkspace();
        ArrayList<Profile> admins = connectedWorkspace.getAdminProfiles();
        if (!admins.contains(connectedProfile)) {
            System.out.println("Action not authorized ! The user is not admin");
            return;
        }
        // admin
        ArrayList<Profile> members = connectedWorkspace.getMemberProfiles();
        String memberId = readString("member's email to kick");
        for(Profile member : members){
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
        //create a user for the user who's creating the workspace
        Profile user;
        user = createProfile(currentConnectedProfile.getId(),workspace.getId());

        //put the creator as an admin
        user.setIsAdminWS(1);
        DAOProfile.update(user);

        return workspace;
    }

    //function called by a user
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

        //putting the user that created it as an admin (to change !)
        String id = currentConnectedProfile.getId()+"."+workspace.getId();
        Profile user = DAOProfile.select(id);
        user.setIsAdminCh(1);
        DAOProfile.update(user);

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
        String id = currentConnectedProfile.getId()+"."+workspace.getId();
        Profile user = DAOProfile.select(id);
        DAOProfile.delete(user);
    }

    //called by a user
    public void quitCh(WorkspaceChannel channel){
        //we actually can't quit a channel
    }

    //called by a user
    public void deleteWs(Workspace workspace) throws SQLException {
        ArrayList<WorkspaceChannel> wsChannel = new ArrayList<WorkspaceChannel>();
        ArrayList<Profile> wsProfiles = new ArrayList<Profile>();

        String idProfile = currentConnectedProfile.getId()+"."+workspace.getId();
        Profile user = DAOProfile.select(idProfile);

        if( user.isAdminWS() == 1 ){
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

    //called by a user
    public void deleteCh(WorkspaceChannel channel){
        ArrayList<Profile> chProfiles = new ArrayList<Profile>();

        if(connectedProfile.getIsAdminCh() == 0){
            System.out.println("you don't have any right on this channel");
        }else {
            //take the role of admin from the profiles that are admins on this channel
            chProfiles = (ArrayList<Profile>) DAOProfile.selectAll();
            for(Profile user : chProfiles){
                if(user.isAdminCh()==1){
                    user.setIsAdminCh(0);
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
        String idProfile = currentConnectedProfile.getId()+"."+workspace.getId();
        Profile user = DAOProfile.select(idProfile);

        Scanner buff;
        if(user.isAdminWS()==0){
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


    public void editCh(WorkspaceChannel channel) throws SQLException {//called by a user
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

    public Message sendChannelMsg(WorkspaceChannel channel){//called by a user
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

    public void deleteChannelMsg(Message msg){//called by a user
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
    public void addWsCollaborator(Profile collab, Workspace workspace) {
        Profile profileCollab = createProfile(collab.getId(),workspace.getId());
        DAOProfile.insert(profileCollab);
    }

    //called by a user to add a collaborator
    public void addChCollaborator(Profile collab) {
        //How to add a collab in a channel ?
        //How can we know that a user /user is in a "X" channel
    }

    //called by a user
    public Profile createProfile(String idUsr,String idWs){
        Profile user = new Profile(idWs,idUsr);
        String id = idUsr+"."+idWs;
        user.setId(id);
        return user;
    }

}
