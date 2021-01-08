package model.user;

import model.HasId;

import model.communication.Workspace;
import model.communication.WorkspaceChannel;

import tool.ListManipulator;

import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Profile : a user that belongs to a certain user in a certain workspace
 */
public class Profile implements HasId {
    /* attributes */

    // composed id as : "userId.workspaceId", belongs_to_one relation type
    private Workspace workspace; // unique workspace related to the Profile
    private User user;// unique user related to the Profile

    // characteristics of a Profile
    private String username; // username
    private String currentStatus; // current status
    private String completeName; // complete name of the user
    private String shownName; // shown name
    private String actualWorkPosition; // actual work position
    private String phoneNumber; // phone number
    private String skypeUserName; // skype user name

    private ZonedDateTime timezone; // timezone

    private boolean isWorkspaceAdmin; // true if the Profile is a workspace admin, false if not

    // belongs_to_many relation type
    private ArrayList<WorkspaceChannel> joinedWorkspaceChannels; // list of workspaces that the User joined weather he created them or not

    // has_many relation type
    private ArrayList<Profile> collaborators; //list of collaborators

    /* constructors */

    /**
     * Constructor of Profile
     *
     * @param user user of the profile
     * @param workspace workspace in which the profile is used
     */
    public Profile(User user, Workspace workspace) {
        this.user = user;
        this.workspace = workspace;
        joinedWorkspaceChannels = new ArrayList<WorkspaceChannel>(); // add the workspace
        username = user.getEmail(); // by default username will be the e-mail address
        currentStatus = "online"; // joined workspace so online
        completeName = user.getEmail(); // by default completeName is the e-mail address
        shownName = user.getEmail(); // by default showName is the e-mail address
        actualWorkPosition = "not mentioned"; // by default the actualWorkPosition is "not mentioned"
        phoneNumber = "not mentioned"; // by default the phoneNumber is "not mentioned"
        skypeUserName = "not mentioned"; // by default the skypeUserName is "not mentioned"
    }

    /* accessors */

    public long getNumberOfWorkspaceChannels(){
        return ListManipulator.numberOfElements(joinedWorkspaceChannels);
    }

    public ArrayList<Profile> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(ArrayList<Profile> collaborators) {
        this.collaborators = collaborators;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public String getShownName() {
        return shownName;
    }

    public void setShownName(String shownName) {
        this.shownName = shownName;
    }

    public String getActualWorkPosition() {
        return actualWorkPosition;
    }

    public void setActualWorkPosition(String actualWorkPosition) {
        this.actualWorkPosition = actualWorkPosition;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSkypeUserName() {
        return skypeUserName;
    }

    public void setSkypeUserName(String skypeUserName) {
        this.skypeUserName = skypeUserName;
    }

    public ZonedDateTime getTimezone() {
        return timezone;
    }

    public void setTimezone(ZonedDateTime timezone) {
        this.timezone = timezone;
    }

    public boolean isWorkspaceAdmin() {
        return isWorkspaceAdmin;
    }

    public void setWorkspaceAdmin(boolean workspaceAdmin) {
        isWorkspaceAdmin = workspaceAdmin;
    }

    public ArrayList<WorkspaceChannel> getJoinedWorkspaceChannels() {
        return joinedWorkspaceChannels;
    }

    public void setJoinedWorkspaceChannels(ArrayList<WorkspaceChannel> joinedWorkspaceChannels) {
        this.joinedWorkspaceChannels = joinedWorkspaceChannels;
    }

    /**
     * @return id of the Profile, as : "userId.workspaceId"
     */
    @Override
    public String getId() {
        return ( user.getId() + "." + workspace.getId() );
    }



    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Profile profile = (Profile) object;

        if (!workspace.equals(profile.workspace)) {
            return false;
        }

        return user.equals(profile.user);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "workspace=" + workspace +
                ", user=" + user +
                ", username='" + username + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                ", completeName='" + completeName + '\'' +
                ", shownName='" + shownName + '\'' +
                ", actualWorkPosition='" + actualWorkPosition + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", skypeUserName='" + skypeUserName + '\'' +
                ", timezone=" + timezone +
                ", isWorkspaceAdmin=" + isWorkspaceAdmin +
                ", joinedWorkspaceChannels=" + joinedWorkspaceChannels +
                ", collaborators=" + collaborators +
                '}';
    }
}

