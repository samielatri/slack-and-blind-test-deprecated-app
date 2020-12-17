package Group;

import UserManagment.User;

import java.util.ArrayList;

public class Workspace {

    private String workspaceName;
    private ArrayList<User> workspaceUsers; // workspace users
    private ArrayList<User> workspaceAdmins ; // workspace admins
    private ArrayList<User> bannedUsers; //users banned from workspace
    private ArrayList<WorkspaceChannel> workspaceChannels;

    public Workspace(String workspaceName){
        workspaceUsers = new ArrayList<User>();
        workspaceAdmins = new ArrayList<User>();
        workspaceChannels = new ArrayList<WorkspaceChannel>();
        this.workspaceName = workspaceName;
    }

    public void deleteUserFromWorkspace(User user){
        for (User u: workspaceUsers){
            if (u==user){
                workspaceUsers.remove(user);
            }
        }
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
}
