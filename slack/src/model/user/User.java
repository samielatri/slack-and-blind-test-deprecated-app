package model.user;

import model.HasId;
import model.communication.Workspace;
import tool.ListManipulator;

import java.util.ArrayList;

/**
 * User : principal actor of the application
 */
public class Profile implements HasId {

    /* attributes */

    // id
    private String email; // email address of the User

    private String password; // password of the User

    // belongs_to_many relation type
    private ArrayList<Workspace> workspaces; // list of workspaces that the User joined weather he created them or not

    // has_many relation type
    private ArrayList<Profile> profiles; // list of profiles that the User has, a User has a profile per workspace

    /* constructors */

    /**
     * Constructor of User
     * @param email
     * @param password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /* accessors */

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return profiles
     */
    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    /**
     * @param profiles
     */
    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    /**
     * @return workspaces
     */
    public ArrayList<Workspace> getWorkspaces() {
        return workspaces;
    }

    /**
     * @param workspaces
     */
    public void setWorkspaces(ArrayList<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    /**
     * @return id of User
     */
    @Override
    public String getId(){
        return this.email;
    }

    /**
     * @param o
     * @return true if equals, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!email.equals(user.email)) return false;
        return password.equals(user.password);
    }

    /**
     * @return information about User
     */
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", workspaces=" + workspaces +
                ", profiles=" + profiles +
                '}';
    }

    public long getNumberOfWorkspaces(){
        return ListManipulator.numberOfElements(workspaces);
    }

    public long getNumberOfProfiles(){
        return ListManipulator.numberOfElements(workspaces);
    }

}
