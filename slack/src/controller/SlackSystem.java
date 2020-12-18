package controller;

import group.*;
import userManagment.*;

import java.util.ArrayList;
import java.util.Scanner;

public class SlackSystem {
    private ArrayList<User> users;
    private ArrayList<Workspace> workspaces;
    private User connectedUser;



    public SlackSystem() {
        users = new ArrayList<User>();
        connectedUser = null;
    }

    public void connection (String username, String password){
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                connectedUser = u;
                return;
            }
        }
    }

    public User deleteUser(User userToDelete){
        userToDelete = exists(userToDelete);

        if (userToDelete != null){
            users.remove(userToDelete);
        }

        return userToDelete;
    }

    public User exists(User searchedUser){
        for(User user : users){
            if(user.equals(searchedUser)){
                return user;
            }
        }
        return null;
    }


    public User register(String email, String password){
        User user = new User(email, email, password);
        if (exists(user) == null){
            users.add(user);
            return user;
        }
        return null;
    }


    public ArrayList<User> getUsers () {
        return users;
    }

    public void setConnectedUser (User connectedUser){
        this.connectedUser = connectedUser;
    }


    public User getConnectedUser () {
        return connectedUser;
    }

    public void checkProfile(){
        if(connectedUser != null) {
            System.out.println(connectedUser.toString());
        } else {
            connectionRequired();
        }
    }

    private void connectionRequired() {
        System.out.println("Connection requise !");
    }

    public void printAllCollaborators(){
        System.out.println(connectedUser.collaboratorsToString());
    }

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
        for (Workspace workspace : workspaces) {
            workspacesString += workspace.getWorkspaceName();
        }
        return workspacesString;
    }

    public void deleteWorkspace (Workspace workspace){

    }


}
