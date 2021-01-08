package model;

import model.communication.Workspace;
import model.communication.WorkspaceChannel;
import model.user.Profile;
import model.user.User;

public class SlackSystem {

    private User currentConnectedUser;
    private Profile currentConnectedProfile;
    private Workspace currentConnectedWorkspace;
    private WorkspaceChannel currentConnectedWorkspaceChannel;

    public User getCurrentConnectedUser() {
        return currentConnectedUser;
    }

    public void setCurrentConnectedUser(User currentConnectedUser) {
        this.currentConnectedUser = currentConnectedUser;
    }

    public Profile getCurrentConnectedProfile() {
        return currentConnectedProfile;
    }

    public void setCurrentConnectedProfile(Profile currentConnectedProfile) {
        this.currentConnectedProfile = currentConnectedProfile;
    }

    public Workspace getCurrentConnectedWorkspace() {
        return currentConnectedWorkspace;
    }

    public void setCurrentConnectedWorkspace(Workspace currentConnectedWorkspace) {
        this.currentConnectedWorkspace = currentConnectedWorkspace;
    }

    public WorkspaceChannel getCurrentConnectedWorkspaceChannel() {
        return currentConnectedWorkspaceChannel;
    }

    public void setCurrentConnectedWorkspaceChannel(WorkspaceChannel currentConnectedWorkspaceChannel) {
        this.currentConnectedWorkspaceChannel = currentConnectedWorkspaceChannel;
    }
}
