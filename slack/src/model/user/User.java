package model.user;

import model.HasId;
import model.group.Workspace;

import java.util.ArrayList;

public class User implements HasId {
    /* attributes */
    private String email; // email address of the user
    private String password; // password of the user
    private ArrayList<Profile> profiles; // list of profiles
    private ArrayList<Workspace> workspaces; // list of joined workspaces weather he created them or not

    /* constructors */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /* accessors */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    public ArrayList<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(ArrayList<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public String getId(){
        return this.email;
    }
}
