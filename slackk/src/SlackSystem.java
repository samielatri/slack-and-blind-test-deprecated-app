import Group.Workspace;
import UserManagment.User;

import java.util.ArrayList;

public class SlackSystem {
    private ArrayList<User> users;
    private ArrayList<Workspace> workspaces;

    public User connection(String username, String password) {
        for(User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;
        }
        return null;
    }

    public String workspaceToString(){
        for (Workspace wsp: workspaces){
            return wsp.getWorkspaceName();
        }
    }

    public void deleteWorkspace(Workspace workspace){

    }







}
