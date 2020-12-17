package Group;

import UserManagment.User;

import java.util.ArrayList;

public class Workspace {
    // UTILISATEURS DU WORKSPACE
    private String workspaceName;
    private ArrayList<User> workspaceUsers;
    private User admin ;
    private ArrayList<WorkspaceChannel> workspaceWorkspaceChannels;

    public Workspace(String workspaceName, User admin){
        this.workspaceName = workspaceName;
        this.admin = admin;

    }

}
