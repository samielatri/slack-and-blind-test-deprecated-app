package model.communication;

import model.HasId;
import model.user.Profile;
import tool.ListManipulator;

import java.util.ArrayList;
import java.util.Objects;

public class Workspace implements HasId {

    /* attributes */

    // id
    private String name; // name of the workspace

    // has_many relation type
    /*private ArrayList<WorkspaceChannel> workspaceChannels; // list of the channels of the workspace
    private ArrayList<Profile> memberProfiles; // list of the users that are members of the workspace (administrators are members too)
    private ArrayList<Profile> adminProfiles; // list of the administrators of the workspace
    private ArrayList<Profile> bannedProfiles; // list of the banned users of the workspace*/

    /* constructors */
    public Workspace(String name){
        this.name = name;
    }

    /* accessors */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public ArrayList<WorkspaceChannel> getWorkspaceChannels() {
        return workspaceChannels;
    }

    public void setWorkspaceChannels(ArrayList<WorkspaceChannel> workspaceChannels) {
        this.workspaceChannels = workspaceChannels;
    }

    public ArrayList<Profile> getMemberProfiles() {
        return memberProfiles;
    }

    public void setMemberProfiles(ArrayList<Profile> memberProfiles) {
        this.memberProfiles = memberProfiles;
    }

    public ArrayList<Profile> getAdminProfiles() {
        return adminProfiles;
    }

    public void setAdminProfiles(ArrayList<Profile> adminProfiles) {
        this.adminProfiles = adminProfiles;
    }

    public ArrayList<Profile> getBannedProfiles() {
        return bannedProfiles;
    }

    public void setBannedProfiles(ArrayList<Profile> bannedProfiles) {
        this.bannedProfiles = bannedProfiles;
    }*/

    @Override
    public String getId() {
        return name;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Workspace workspace = (Workspace) o;

        if (!name.equals(workspace.name)) return false;
        if (!Objects.equals(workspaceChannels, workspace.workspaceChannels))
            return false;
        if (!memberProfiles.equals(workspace.memberProfiles)) return false;
        if (!adminProfiles.equals(workspace.adminProfiles)) return false;
        return Objects.equals(bannedProfiles, workspace.bannedProfiles);
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "name='" + name + '\'' +
                ", workspaceChannels=" + workspaceChannels +
                ", memberProfiles=" + memberProfiles +
                ", adminProfiles=" + adminProfiles +
                ", bannedProfiles=" + bannedProfiles +
                '}';
    }


    /**
     * @return
     *
    public long getNumberOfWorkspaceChannels(){
        return ListManipulator.numberOfElements(workspaceChannels);
    }

    /**
     *
     *
    public long getNumberOfMembers(){
        return ListManipulator.numberOfElements(memberProfiles);
    }

    /**
     *
     * @return
     *
    public long getNumberOfAdmins(){
        return ListManipulator.numberOfElements(adminProfiles);
    }

    /**
     *
     * @return
     *
    public long getNumberOfBannedMembers(){
        return ListManipulator.numberOfElements(bannedProfiles);
    }*/

    /*
     *
     * @return
     *
    public long getNumberOfSimpleMembers(){
        return getNumberOfMembers() - getNumberOfAdmins();
    }*/
}
