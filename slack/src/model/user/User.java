package model.user;

import model.HasId;
import model.communication.Workspace;
import tool.ListManipulator;

import java.util.ArrayList;

/**
 * User : principal actor of the application
 */
public class User implements HasId {

    /* attributes */

    // id
    private String email; // email address of the User

    private String password; // password of the User

    // belongs_to_many relation type
    private ArrayList<Workspace> workspaces; // list of workspaces that the User joined weather he created them or not

    // has_many relation type
    private ArrayList<Profile> profiles; // list of profiles that the User has, a User has a user per workspace

    /* constructors */

    /**
     * Constructor of User
     * @param email email of the User
     * @param password password of the User
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /* accessors */

    /**
     * @return email of the User
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email email of the User
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return password of the User
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password password of the User
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return profiles of the User
     */
    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    /**
     * @param profiles profiles of the User
     */
    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    /**
     * @return workspaces of the User
     */
    public ArrayList<Workspace> getWorkspaces() {
        return workspaces;
    }

    /**
     * @param workspaces workspaces of the User
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
     * @param object object
     * @return true if equals, false if not
     */
    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }

        if (object == null || getClass() != object.getClass()){
            return false;
        }

        User user = (User) object;

        if (!email.equals(user.email)){
            return false;
        }

        return (password.equals(user.password));
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

    /**
     * @return number of workspaces
     */
    public long getNumberOfWorkspaces(){
        return ListManipulator.numberOfElements(workspaces);
    }

    /**
     * @return number of profiles
     */
    public long getNumberOfProfiles(){
        return ListManipulator.numberOfElements(workspaces);
    }

}
