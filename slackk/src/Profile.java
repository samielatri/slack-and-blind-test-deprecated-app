import java.util.Objects;

public class Profile {
    User user; // user that has the profile
    Workspace workspace; // the workspace that is related to the user

    String currentStatus; // current status in a specific workspace
    String completeName; // complete name of the user in a specific workspace
    String shownName; // shown name in a specific workspace
    String actualWorkPosition; // actual work position in a specific workspace
    String phoneNumber; // phone number in a specific workspace
    String timezone; // timezone in a specific workspace
    String profilePicture; // profile picture in a specific workspace

    /* constructor */

    private Profile(){}

    public Profile(User user, Workspace workspace){
        this.user = user;
        this.workspace = workspace;
        this.currentStatus = "";
        this.completeName = "";
        this.shownName="";
        this.actualWorkPosition="";
        this.phoneNumber = "";
        this.timezone="";
        this.profilePicture="";
    }

    public Profile(Workspace workspace, User user){
        this(user, workspace);
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Profile profile = (Profile) o;
        // field comparison
        return  user.equals(profile.user)
                && workspace.equals(profile.workspace)
                && Objects.equals(currentStatus, profile.currentStatus)
                && Objects.equals(completeName, profile.completeName)
                && Objects.equals(shownName, profile.shownName)
                && Objects.equals(actualWorkPosition, profile.actualWorkPosition)
                && Objects.equals(phoneNumber, profile.phoneNumber)
                && Objects.equals(timezone, profile.timezone)
                && Objects.equals(profilePicture, profile.profilePicture);

    }

    /* toString */
    @Override
    public String toString(){
        return "\nProfile de l'utilisateur " + user.toString() + " dans l'espace de travail" + workspace.toString() + "\n"
                + "\t"  + "Status : " + currentStatus
                        + "Nom complet : " + completeName
                        + "Nom montré : " + shownName
                        + "Poste actuel : " + actualWorkPosition
                        + "Numéro de téléphone : " + phoneNumber
                        + "fuseau horaire : " + timezone
                + "\n";
    }

    /* accessors */

    // getters
    public String getActualWorkPosition() {
        return actualWorkPosition;
    }

    public String getCompleteName() {
        return completeName;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getShownName() {
        return shownName;
    }

    public String getTimezone() {
        return timezone;
    }

    public User getUser() {
        return user;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    // setters
    private void setActualWorkPosition(String actualWorkPosition) {
        this.actualWorkPosition = actualWorkPosition;
    }

    private void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    private void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    private void setShownName(String shownName) {
        this.shownName = shownName;
    }

    private void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    private void setUser(User user) {
        this.user = user;
    }

    private void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

}

