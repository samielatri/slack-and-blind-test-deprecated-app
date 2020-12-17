package Group;

import UserManagment.User;

import java.util.ArrayList;

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

    public void addUserToWorkspace(User user){
        workspaceUsers.add(user);
    }

    /**
     *
     * @param user
     */
    public void deleteUserFromWorkspace(User user){
        for (User u: workspaceUsers){
            if (u==user){
                workspaceUsers.remove(user);
            }
        }
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean isSimpleUser(User user){
        return (isUser(user)&&!isAdmin(user));
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean isUser(User user){
        for (User u: workspaceUsers){
            if (u==user){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean isAdmin(User user){
        for (User u: workspaceAdmins){
            if (u==user){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean isBannedUser(User user){
        for (User u: bannedUsers){
            if (u==user){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param user
     */
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

    /**
     *
     * @return
     */
    public ArrayList<WorkspaceChannel> getWorkspaceChannels() {
        return workspaceChannels;
    }

    /**
     *
     * @return
     */
    public ArrayList<User> getWorkspaceAdmins() {
        return workspaceAdmins;
    }

    /**
     *
     * @param workspaceAdmins
     */
    public void setWorkspaceAdmins(ArrayList<User> workspaceAdmins) {
        this.workspaceAdmins = workspaceAdmins;
    }

    /**
     *
     * @param workspaceUsers
     */
    public void setWorkspaceUsers(ArrayList<User> workspaceUsers) {
        this.workspaceUsers = workspaceUsers;
    }

    /**
     *
     * @param workspaceChannels
     */
    public void setWorkspaceChannels(ArrayList<WorkspaceChannel> workspaceChannels) {
        this.workspaceChannels = workspaceChannels;
    }
}
