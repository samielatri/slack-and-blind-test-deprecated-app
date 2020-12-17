import Group.Workspace;
import UserManagment.User;

import java.util.ArrayList;

public class SlackSystem {
    private ArrayList<User> users;
    private ArrayList<Workspace> workspaces;
    private User connectedUser;


    /**
     *
     */
    public SlackSystem() {
        users = new ArrayList<User>();
        connectedUser = null;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public void connection (String username, String password){
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                connectedUser = u;
                return;
            }
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<User> getUsers () {
        return users;
    }

    /**
     *
     * @param connectedUser
     */
    public void setConnectedUser (User connectedUser){
        this.connectedUser = connectedUser;
    }

    /**
     *
     * @return
     */
    public User getConnectedUser () {
        return connectedUser;
    }

    /**
     *
     */
    public void checkProfile(){
        System.out.println(getConnectedUser().toString());
    }

    public String workspacesToString(){
        String workspacesString = "";
        for (Workspace wsp : workspaces) {
            workspacesString += wsp.getWorkspaceName();
        }
        return workspacesString;
    }

    public void deleteWorkspace (Workspace workspace){

    }
}
