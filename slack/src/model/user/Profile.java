package model.user;

import model.HasId;
import model.group.Workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Profile implements HasId {
    /* attributes */
    private String id;

    private String idWs; // worksapace of the profile
    private String idUser; // user of the profile

    private String username; // username of the user
    private String currentStatus; // current status in a specific workspace
    private String completeName; // complete name of the user in a specific workspace
    private String shownName; // shown name in a specific workspace
    //private String actualWorkPosition; // actual work position in a specific workspace
    private String phoneNumber; // phone number in a specific workspace
    private String timezone; // timezone in a specific workspace
    private File profilePicture; // profile picture in a specific workspace
    private int isAdminWS = 0;
    private int isAdminCh = 0;



    /* constructors */
    public Profile(String idWs, String idUser) {
        this.idWs = idWs;
        this.idUser = idUser;

    }

    /* accessors */
    public String getId() {
        return idUser+"."+idWs;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsAdminWS() {
        return isAdminWS;
    }

    public void setIsAdminWS(int isAdminWS) {
        this.isAdminWS = isAdminWS;
    }

    public int getIsAdminCh() {
        return isAdminCh;
    }

    public void setIsAdminCh(int isAdminCh) {
        this.isAdminCh = isAdminCh;
    }

    public String getIdWs() {
        return idWs;
    }

    public void setIdWs(String IdWs) {
        this.idWs = idWs;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
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

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public File getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(File profilePicture) {
        this.profilePicture = profilePicture;
    }
}

