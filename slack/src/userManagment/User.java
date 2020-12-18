package userManagment;

import group.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class User {
    /* attributes */
    private ArrayList<Workspace> createdWS; // list of all Workspaces created by the user
    private ArrayList<Workspace> joinedWS; // list of all Workspaces joined by the user
    private ArrayList<Profile> profiles; // list of all profiles related to specific workspaces
    private ArrayList<WorkspaceChannel> createdWorkspaceChannels; // list of all channels created by the user
    private ArrayList<WorkspaceChannel> joinedWorkspaceChannels; // list of all channels joined by the user
    private ArrayList<Message> sentMessages; // list of all messages sent by the user
    private ArrayList<User> collaborators; // list of all collaborators
    private String mailAddress; // mail address of the user
    private String username;  // username of the user
    private String password; // password of the user

    /* constructors */
    public User(String mailAddress, String username, String password){
        this.mailAddress = mailAddress;
        this.username = username;
        this.password = password;
        this.createdWorkspaceChannels = new ArrayList<WorkspaceChannel>();
        this.joinedWorkspaceChannels =  new ArrayList<WorkspaceChannel>();
        this.sentMessages = new ArrayList<Message>();
        this.profiles = new ArrayList<Profile>();
    }

    public Workspace creatWs(){
        Workspace workspace;
        String wsName;
        Scanner buff;
        buff= new Scanner(System.in);
        wsName= buff.nextLine();
        workspace = new Workspace(wsName);
        (workspace.getWorkspaceAdmins()).add(this);
        (workspace.getWorkspaceUsers()).add(this);
        return workspace;
    }

    public void editWorkspace(Workspace workspace){//faire un switch case si l'usr veux supprimer un membre
        if((workspace.getWorkspaceAdmins()).contains(this)) {
            String newName;
            Scanner buff = new Scanner(System.in);
            newName = buff.nextLine();
            workspace.setWorkspaceName(newName);
        }else{
            System.out.println("you have not right on this workspace");
        }
    }
    public void deleteWs(){
        if(createdWS.isEmpty()){
            System.out.println("you don't have any workspace");
        }else{
            System.out.println("please enter the id of the workspace you want to delete :");
            Scanner buffer = new Scanner(System.in);
            int id = buffer.nextInt();
            for(Workspace ws: createdWS) {
                if (ws.getIdWs() == id) {
                    createdWS.remove(ws);
                    System.out.println("this workSpace has been deleted successfully");
                }else {
                    System.out.println("this id does not refere to any workspace");
                }
            }
        }
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
        int intInput = 0;
        do {
            System.out.println("edit :\n\t1- email address\n\t2- password\n\t 3-return");
            intInput = readInt("your choice");
        } while (intInput > 0 && intInput < 3);
        if (intInput == 1){
            do {
                newEmailAddress = readString("new email address");
                setMailAddress(newEmailAddress);
                System.out.println("Email Address changed successfully");
            }while(! isValidEmailAddress(newEmailAddress));
        }
        if (intInput== 2) {
            currentPassword = readString("current password");
            if (currentPassword == password) {
                do {
                    newPassword = readString("new password");
                    newPasswordConfirm = readString("new password confirmation");
                    passwordConfirmed = (newPassword == newPasswordConfirm);
                    if (passwordConfirmed) {
                        setPassword(newPassword);
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

    public boolean isValidEmailAddress(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /* accessors */

    //gettors
    public ArrayList<WorkspaceChannel> getJoinedChannels() {

        return joinedWorkspaceChannels;
    }

    public ArrayList<Message> getSentMessages() {

        return sentMessages;
    }

    public ArrayList<User> getCollaborators() {

        return collaborators;
    }

    public String getMailAddress() {

        return mailAddress;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {

        return password;
    }

    public ArrayList<WorkspaceChannel> getCreatedChannels() {
        return createdWorkspaceChannels;
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public ArrayList<WorkspaceChannel> getCreatedWorkspaceChannels() {
        return createdWorkspaceChannels;
    }

    public ArrayList<WorkspaceChannel> getJoinedWorkspaceChannels() {
        return joinedWorkspaceChannels;
    }

    // setters
    private void setCreatedChannels(ArrayList<WorkspaceChannel> createdWorkspaceChannels) {
        this.createdWorkspaceChannels = createdWorkspaceChannels;
    }

    private void setCollaborators(ArrayList<User> collaborators) {

        this.collaborators = collaborators;
    }

    private void setJoinedChannels(ArrayList<WorkspaceChannel> joinedWorkspaceChannels) {
        this.joinedWorkspaceChannels = joinedWorkspaceChannels;
    }

    private void setMailAddress(String mailAddress) {

        this.mailAddress = mailAddress;
    }

    private void setPassword(String password) {

        this.password = password;
    }

    private void setSentMessages(ArrayList<Message> sentMessages) {

        this.sentMessages = sentMessages;
    }

    private void setUsername(String username) {

        this.username = username;
    }

    private void setProfiles(ArrayList<Profile> profiles) {

        this.profiles = profiles;
    }

    public void setCreatedWorkspaceChannels(ArrayList<WorkspaceChannel> createdWorkspaceChannels) {
        this.createdWorkspaceChannels = createdWorkspaceChannels;
    }

    public void setJoinedWorkspaceChannels(ArrayList<WorkspaceChannel> joinedWorkspaceChannels) {
        this.joinedWorkspaceChannels = joinedWorkspaceChannels;
    }


    /* Methods */

    /*public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        User user = (User) o;
        // field comparison
        return  username.equals(user.username)
                && password.equals(user.password);
    }*/

    public Message sendMsg(Object canal){
        // determine which canal it is
        String msg;
        Date date = new Date();
        Scanner buffer = new Scanner(System.in);
        msg = buffer.next();
        // addCollabteur(user);
        // if (updatedAt == null) {
        //   this.createdAt = new Date();
        // }
        // if (createdAt != null) {
        //     this.updatedAt = updatedAt;
        // }
        return new Message(this, msg, canal);
    }

    public Message sendMsgChannel(WorkspaceChannel wc){
        if(joinedWorkspaceChannels.contains(wc)){
            return sendMsg(this);
        }
        return null; // qui a developper cette methode ?
    }

    public Message sendMsgCollaborator(User collab){
        if(collaborators.contains(collab)){
            return sendMsg(this);
        }
        return null;
    }





    public void deleteMsg(){
        if(sentMessages.isEmpty()){
            System.out.println("you don't have any message");
        }else{
            System.out.println("please enter the id of the message :");
            Scanner buffer = new Scanner(System.in);
            int idmsg = buffer.nextInt();
            if(idmsg>=0 && idmsg<sentMessages.size()){
                Message m=sentMessages.get(idmsg);
                sentMessages.remove(m);
                System.out.println("this message has been deleted successfully");
            }else{
                System.out.println("Group.Message not found");
            }

        }
    }


    public void editMsg(){
        if(sentMessages.isEmpty()){
            System.out.println("you don't have any message");
        }else{
            System.out.println("please enter the id of the message you want edit :");
            Scanner buffer = new Scanner(System.in);
            int idmsg = buffer.nextInt();
            if(idmsg>=0 && idmsg<sentMessages.size()){
                Message m=sentMessages.get(idmsg);
                System.out.println("Edit your message here: ");
                String edit=buffer.next();
                //m= new Group.Message(edit,m.getCreatedAt(),new Date(), m.username);
                System.out.println("this message has been deleted successfully");
            }else{
                System.out.println("Group.Message not found");
            }

        }
    }


    public void addFriend(User user){
        collaborators.add(user);
    }

    public void inviterFriend(User user){
        if (joinedWorkspaceChannels.contains(user)) {
            System.out.println(this.toString() + " envoie une demande d'amis à " + user.toString() + " dans le serveur " + joinedWorkspaceChannels.toString());
        } else {
            System.out.println("Utilisateur inexistant dans le channel actuel");
        }
    }

    public void inviteChannel(){

    }

    public void leaveChannel(){

    }

    public void deleteCollaborator(User user){
        for (User u : collaborators) {
            if (isCollaborator(user)) {
                collaborators.remove(user);
            }
        }
    }

    public boolean isCollaborator(User user){
        return collaborators.contains(user);
    }

    public User getCollaborator(User collaborator){
       if (isCollaborator(collaborator)) {
           return collaborator;
       }
       return null;
    }

    public int getNumberOfCollaborators(){
        return collaborators.size();
    }

    public String collaboratorsToString(){
        int id = 0 ;
        String collaboratorsString = "";
        for (User collaborator : collaborators){
            id ++;
            collaboratorsString += id + " - " + collaborator.toString() + "\n";
        }
        return collaboratorsString;
    }


    /* toString */
    @Override

    public String toString() {
        return "Bienvenue" + username;
    }

}

