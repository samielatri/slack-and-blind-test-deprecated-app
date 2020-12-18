package group;

import UserManagment.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Workspace {

    private String workspaceName;
    private ArrayList<User> workspaceUsers; // workspace users
    private ArrayList<User> workspaceAdmins ; // workspace admins
    private ArrayList<User> bannedUsers; //users banned from workspace
    private ArrayList<WorkspaceChannel> workspaceChannels;
    private static int idWs = 0;

    public Workspace(String workspaceName){
        workspaceUsers = new ArrayList<User>();
        workspaceAdmins = new ArrayList<User>();
        workspaceChannels = new ArrayList<WorkspaceChannel>();
        this.workspaceName = workspaceName;
        idWs++;
    }
    public WorkspaceChannel createCh(User creator){
        WorkspaceChannel chan;
        String chName;

        Scanner buffer;
        boolean exist = false;
        do{
            buffer = new Scanner(System.in);
            chName = buffer.nextLine();
            for(WorkspaceChannel channel: creator.getCreatedChannels()) {
                if (channel.getNameCh().equals(chName)) {
                    System.out.println("this name already exist choose another name");
                    exist = true;
                }
            }
        }while(exist);
        chan = new WorkspaceChannel(chName,creator);
        creator.getCreatedChannels().add(chan);
        chan.getChannelMembers().addAll(workspaceAdmins);
        workspaceChannels.add(chan);
        //choose if you want it to be private or not
        int choix;
        System.out.println("do you want this channel to be private ?");
        System.out.println("1- yes");
        System.out.println("0- No");
        Scanner buff = new Scanner(System.in);
        choix = buff.nextInt();
        if(choix == 1) {
            chan.setPrivateCh(true);
            System.out.println("this created channel is private");
        }else{
            chan.setPrivateCh(false);
            chan.getChannelMembers().addAll(workspaceUsers);
        }
        return chan;
    }

    public void deleteCh(User creator){
        if(creator.getCreatedChannels().isEmpty()){
            System.out.println("you don't have any right on any channel");
        }else{
            System.out.println("please enter the name of the channel you want to delete :");
            Scanner buffer = new Scanner(System.in);
            String chName = buffer.nextLine();
            for(WorkspaceChannel channel: creator.getCreatedChannels()) {
                if (channel.getNameCh().equals(chName)) {
                    creator.getCreatedChannels().remove(channel);
                    workspaceChannels.remove(channel);
                    System.out.println("this channel has been deleted successfully");
                }else {
                    System.out.println("this channel doesn't exist");
                }
            }
        }
    }
    public void editChannel(WorkspaceChannel channel,User user){//faire un switch case si l'usr veux supprimer un membre
        if((channel.getAdminChannel()).contains(user)) {
            String newName;
            Scanner buff = new Scanner(System.in);
            newName = buff.nextLine();
            channel.setNameCh(newName);
        }else{
            System.out.println("you have no right on this workspace");
        }
    }

    public void addUserToWorkspace(User user){
        workspaceUsers.add(user);
    }

    public void deleteUserFromWorkspace(User user){
        for (User u: workspaceUsers){
            if (u==user){
                workspaceUsers.remove(user);
            }
        }
    }

    public boolean isSimpleUser(User user){

        return (isUser(user)&&!isAdmin(user));
    }

    public boolean isUser(User user){
        for (User u: workspaceUsers){
            if (u==user){
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin(User user){
        for (User u: workspaceAdmins){
            if (u==user){
                return true;
            }
        }
        return false;
    }

    public boolean isBannedUser(User user){
        for (User u: bannedUsers){
            if (u==user){
                return true;
            }
        }
        return false;
    }

    public void banFromWorkspace(User user){
        if (isBannedUser(user)){
            System.out.println(user.toString() + " is banned !");
            deleteUserFromWorkspace(user);
        }
    }

    public String getWorkspaceName(){

        return workspaceName;
    }

    public ArrayList<User> getWorkspaceUsers() {

        return workspaceUsers;
    }

    public ArrayList<WorkspaceChannel> getWorkspaceWorkspaceChannels() {

        return workspaceChannels;
    }

    public ArrayList<WorkspaceChannel> getWorkspaceChannels() {

        return workspaceChannels;
    }

    public ArrayList<User> getWorkspaceAdmins() {

        return workspaceAdmins;
    }

    public void setWorkspaceAdmins(ArrayList<User> workspaceAdmins) {

        this.workspaceAdmins = workspaceAdmins;
    }

    public void setWorkspaceUsers(ArrayList<User> workspaceUsers) {

        this.workspaceUsers = workspaceUsers;
    }

    public void setWorkspaceChannels(ArrayList<WorkspaceChannel> workspaceChannels) {
        this.workspaceChannels = workspaceChannels;
    }
    public void setWorkspaceName(String name){
        this.workspaceName = name;
    }
    public int getIdWs(){
        return idWs;
    }
}
