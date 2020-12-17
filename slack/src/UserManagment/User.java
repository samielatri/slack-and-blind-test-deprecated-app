package UserManagment;

import Group.WorkspaceChannel;
import Group.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class User {
    /* attributes */
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


    public User(String mailAddress, String password) {
        this(mailAddress, mailAddress, password); // mailAdress to modify with static id name
    }

    /* accessors */

    // gettors
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
    /**
     *
     */
    public User addCollaborator(User user){
        if (!collaborators.contains(user)){
            collaborators.add(user);
            return user;
        }
        return null;
    }

    /***
     *
     */
    public boolean equals(Object o) {
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
        return  user.equals(user.username)
                && user.equals(user.password);
    }
    /***
     *
     * @param canal
     * @return
     */
    public Message sendMsg(Object canal){
        // determine which canal it is
        String msg;
        Date date = new Date();
        Scanner buffer = new Scanner(System.in);
        msg = buffer.next();
        //addCollabteur(user);
        //if (updatedAt == null) {
          //  this.createdAt = new Date();
        //}
        //if (createdAt != null) {
          //  this.updatedAt = updatedAt;
       // }
        return new Message(this, msg, canal);
    }

    /** send message in the channel
     * method added by Nahidath
     * @param wc
     * @return
     */
    public Message sendMsgChannel(WorkspaceChannel wc){
        if(joinedWorkspaceChannels.contains(wc)){
            return sendMsg(this);
        }
        return null;
    }

    /** send message to the collaborator
     *  methode added by Nahidath
     * @param collab
     * @return
     */
    public Message sendMsgCollaborator(User collab){
        if(collaborators.contains(collab)){
            return sendMsg(this);
        }
        return new Message(null, null, null);
    }



    /***
     *
     * @return
     */
    public WorkspaceChannel createCh(){
        String nom;
        Scanner buffer;
        do{
            buffer = new Scanner(System.in);
            nom = buffer.next();
            WorkspaceChannel chan = new WorkspaceChannel(nom,this);
            if(createdWorkspaceChannels.contains(chan)){
                System.out.println("this channel already exist");
            }
        }while(true);//createdChannels.contains(chan));
        //createdChannels.add(chan);
        //return chan;
    }

    /***
     *
     */
    public void deleteCh(){
        if(createdWorkspaceChannels.isEmpty()){
            System.out.println("you don't have any right on channel, call jesus");
        }else{
            System.out.println("please enter the name of the channel to delete :");
            Scanner buffer = new Scanner(System.in);
            String nom = buffer.next();
            WorkspaceChannel c=new WorkspaceChannel(nom,this);
            if(createdWorkspaceChannels.contains(c)){
                createdWorkspaceChannels.remove(c);
                System.out.println("this channel has been deleted successfully");
            }else{
                System.out.println("this channel doesn't exist");
            }

        }
    }

    /***
     *
     */
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

    /***
     *
     */
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

    /***
     *
     * @param user
     */
    public void addFriend(User user){
        collaborators.add(user);
    }

    /**
     *
     * @param user
     */
    public void inviterFriend(User user){
        if (joinedWorkspaceChannels.contains(user)) {
            System.out.println(this.toString() + " envoie une demande d'amis à " + user.toString() + " dans le serveur " + joinedWorkspaceChannels.toString());
        } else {
            System.out.println("Utilisateur inexistant dans le channel actuel");
        }
    }

    /**
     *
     */
    public void inviteChannel(){

    }

    /**
     *
     */
    public void leaveChannel(){

    }

    /**
     *
     * @param user
     */
    public void deleteCollaborator(User user){
        for (User u : collaborators) {
            if (isCollaborator(user)) {
                collaborators.remove(user);
            }
        }
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean isCollaborator(User user){
        return collaborators.contains(user);
    }

    /**
     *
     * @param collaborator
     * @return
     */
    public User getCollaborator(User collaborator){
       if (isCollaborator(collaborator)) {
           return collaborator;
       }
       return null;
    }

    /**
     *
     */
    public int getNumberOfCollaborators(){
        return collaborators.size();
    }

    /**
     *
     * @return
     */
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
    /**
     *
     */
    public String toString() {
        return "Bienvenue" + username;
    }

}
