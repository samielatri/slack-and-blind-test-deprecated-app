import Group.Workspace;
import UserManagment.User;

import java.util.ArrayList;
import java.util.Scanner;

public class SlackSystem {
    private ArrayList<User> users;
    private ArrayList<Workspace> workspaces;
    private User connectedUser;


    /**
     * Constructor
     */
    public SlackSystem() {
        users = new ArrayList<User>();
        connectedUser = null;
    }

    /**
     * Connection
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
     * getUsers()
     * @return
     */
    public ArrayList<User> getUsers () {
        return users;
    }

    /**
     * setConnectedUser
     * @param connectedUser
     */
    public void setConnectedUser (User connectedUser){
        this.connectedUser = connectedUser;
    }

    /**
     * getConnectedUser
     * @return
     */
    public User getConnectedUser () {
        return connectedUser;
    }


    /**
     * checkProfile
     */
    public void checkProfile(){
        if(connectedUser != null) {
            System.out.println(connectedUser.toString());
        } else {
            connectionRequired();
        }
    }

    /**
     *
     */
    private void connectionRequired() {
        System.out.println("Connection requise !");
    }

    /**
     *
     */
    public void printAllCollaborators(){
        System.out.println(connectedUser.collaboratorsToString());
    }


    /***
     *
     */
    public void checkCollaboratorProfile(){
        printAllCollaborators();
        int input = -1;
        Scanner buffer = new Scanner(System.in);
        do {
            input = buffer.nextInt();
        } while (input > 0 && input < connectedUser.getNumberOfCollaborators());
        connectedUser.getCollaborators().indexOf(input);
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
