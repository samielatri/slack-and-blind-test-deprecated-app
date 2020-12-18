package userManagment;

import group.Workspace;
import group.WorkspaceChannel;
import group.Message;

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

    public Workspace createWs(){
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
    public void ShowListOfWs(ArrayList<Workspace> workspaces){
        for(Workspace workspace: workspaces){
            System.out.println(workspace);
        }
    }
    public void editAccount(){}
    public void editProfile(){}
    public void joinWorkSpace(){}
    public Workspace selectWorkspace(){return null;}
    public WorkspaceChannel selectChannel(){return null;}
    /* accessors */

    //gettors

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


    public void addCollaborator(User user){
        collaborators.add(user);
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

