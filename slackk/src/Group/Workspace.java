package Group;

import UserManagment.User;

import java.util.ArrayList;

public class Workspace {

    private ArrayList<User> workspaceUsers; // workspace users
    private ArrayList<User> workspaceAdmins ; // workspace admins
    private ArrayList<WorkspaceChannel> workspaceChannels;

    public Workspace(){
        workspaceUsers = new ArrayList<User>();
        workspaceAdmins = new ArrayList<User>();
        workspaceChannels = new ArrayList<WorkspaceChannel>();
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
