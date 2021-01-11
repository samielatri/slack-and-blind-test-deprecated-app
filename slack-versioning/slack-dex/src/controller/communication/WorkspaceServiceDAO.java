package controller.communication;


import controller.user.ProfileServiceDAO;
import database.DAO;
import database.DAOFactory;
import database.SQLProfileDAO;
import model.SlackSystem;
import model.communication.Workspace;
import model.user.Profile;
import model.user.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceServiceDAO {
    private ProfileServiceDAO profileServiceDAO=new ProfileServiceDAO();
    private DAO<Workspace> DAOWorkspace= DAOFactory.workspace();
    private DAO<Profile> DAOProfile=DAOFactory.profile();
    private SlackSystem slackSystem;

    public WorkspaceServiceDAO(SlackSystem slackSystem) throws SQLException {
        this.slackSystem=slackSystem;
    }


    /******************************************

    public void connectWorkspace() {
        User user = slackSystem.getConnectedUser();
        ArrayList<Workspace> listWorskspace = user.getWorkspaces();
        String workspaceId = readString("workspace id to join");
        for (Workspace tempWorkspace : listWorskspace) {
            String tempWorkspaceId = tempWorkspace.getId();
            if (workspaceId == tempWorkspaceId) {
                System.out.println("Connectiong to workspace ...");
                user.setCurrentWorkspace(tempWorkspace);
                System.out.println("Connected to the workspace successfully !");
                return;
            }
        }
        // didn't find and didn't join worskapce
        System.out.println("Workspace is not joined");
    }*/

    public void joinWorkspace(String workspaceId) throws SQLException {
        User user = slackSystem.getCurrentConnectedUser();

        Profile profile = DAOProfile.select(user.getId() + "." + workspaceId);
        if(profile == null){
            profile = profileServiceDAO.createProfile(user.getId(), workspaceId);
        }
        slackSystem.setCurrentConnectedProfile(profile);
        slackSystem.setCurrentConnectedWorkspace(DAOWorkspace.select(workspaceId));
    }

    /*
    public void banMemberFromWorkspace() {
        User connectedUser = slackSystem.getConnectedUser();
        Workspace connectedWorkspace = connectedUser.getCurrentWorkspace();
        ArrayList<User> admins = connectedWorkspace.getAdminProfiles();
        if (!admins.contains(connectedUser)) {
            System.out.println("Action not authorized ! The user is not admin");
            return;
        }
        // admin
        ArrayList<User> members = connectedWorkspace.getMemberProfiles();
        String memberId = readString("member's email to ban");
        for (User member : members) {
            if (member.getEmail() == memberId) {
                System.out.println("Banning member...");
                connectedWorkspace.getMemberProfiles().remove(member);
                if (admins.contains(member)) {
                    connectedWorkspace.getAdminProfiles().remove(member);
                }
                connectedWorkspace.getBannedProfiles().add(member);
                System.out.println("Member banned");
                return;
            }
        }
        System.out.println("Member not found");
    }

    public void kickMemberFromWorkspace() {
        User connectedUser = slackSystem.getConnectedUser();
        Workspace connectedWorkspace = connectedUser.getCurrentWorkspace();
        ArrayList<User> admins = connectedWorkspace.getAdminProfiles();
        if (!admins.contains(connectedUser)) {
            System.out.println("Action not authorized ! The user is not admin");
            return;
        }
        // admin
        ArrayList<User> members = connectedWorkspace.getMemberProfiles();
        String memberId = readString("member's email to kick");
        for (User member : members) {
            if (member.getEmail() == memberId) {
                System.out.println("kicking member...");
                connectedWorkspace.getMemberProfiles().remove(member);
                if (admins.contains(member)) {
                    connectedWorkspace.getAdminProfiles().remove(member);
                }
                System.out.println("Member kicked");
                return;
            }
        }
        System.out.println("Member not found");
    }*/

    //function called by a user
    public Workspace createWs(String workspaceName) throws SQLException {
        Workspace workspace=null,ws;

        /*String workspaceName;
        Scanner scanner;*/

        ws = DAOWorkspace.select(workspaceName);
        if(ws != null){
            System.out.println("This name already exist please enter another one");
            return null;
        }

        //create a profile for the user who's creating the workspace
        Profile profile;
        profile = profileServiceDAO.createProfile(slackSystem.getCurrentConnectedUser().getId(),workspace.getId());
        workspace = new Workspace(workspaceName,profile);
        ws = DAOWorkspace.insert(workspace);
        //put the creator as an admin
        profile.setIsWorkspaceAdmin(true);
        DAOProfile.update(profile);

        return workspace;
    }

    //called by a user
    /*public void quitWs(Workspace workspace) throws SQLException {
        String id = currentConnectedUser.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(id);
        DAOProfile.delete(profile);
    }

    //called by a user
    public void deleteWs(Workspace workspace) throws SQLException {
        ArrayList<WorkspaceChannel> wsChannel = new ArrayList<WorkspaceChannel>();
        ArrayList<Profile> wsProfiles = new ArrayList<Profile>();

        String idProfile = currentConnectedUser.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(idProfile);

        if( profile.isAdminWS() == 1 ){
            //delete all channels of this workspace
            wsChannel = (ArrayList<WorkspaceChannel>) DAOChannel.selectAll();
            for(WorkspaceChannel channel : wsChannel){
                if(channel.getWsId()==workspace.getId()){
                    DAOChannel.delete(channel);
                }
            }
            wsProfiles = (ArrayList<Profile>) DAOProfile.selectAll();

            //delete all its profiles
            for(Profile p : wsProfiles){
                if(p.getWorkspaceId()==workspace.getId()){
                    DAOProfile.delete(p);
                }
            }

            //delete the workspace
            DAOWorkspace.delete(workspace);
            System.out.println("this workspace has been deleted successfully");

        }else{
            System.out.println("you don't have any right on this workspace");
        }

    }

    public void editWs(Workspace workspace) throws SQLException {//called by a user
        Workspace ws;
        ArrayList<Profile> wsProfiles = new ArrayList<Profile>();
        ArrayList<WorkspaceChannel> wsChannels = new ArrayList<WorkspaceChannel>();
        String newName;
        boolean exist = false;
        String idProfile = currentConnectedUser.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(idProfile);

        Scanner buff;
        if(profile.isAdminWS()==0){
            System.out.println("you don't have any right on this workspace");
        }else{
            System.out.println("Enter the new name of this workspace");
            do {
                buff = new Scanner(System.in);
                newName = buff.nextLine();
                ws = DAOWorkspace.select(newName);
                if(ws != null){
                    exist = true;
                    System.out.println("This name is taken, please choose another one");
                }
            }while(exist);
            //change the id workspace for all the channels
            wsChannels = (ArrayList<WorkspaceChannel>) DAOChannel.selectAll();
            for(WorkspaceChannel channel: wsChannels){
                if(channel.getWsId()==workspace.getId()){
                    channel.setWsId(newName);
                    DAOChannel.update(channel);
                }
            }
            //change the id workspace for all the profiles
            wsProfiles = (ArrayList<Profile>) DAOProfile.selectAll();
            for(Profile p: wsProfiles){
                if(p.getWorkspaceId()==workspace.getId()){
                    p.setWorkspaceId(newName);
                    DAOProfile.update(p);
                }
            }
            //update the workspace
            workspace.setName(newName);
            ws = DAOWorkspace.update(workspace);
            System.out.println("The workspace informations has been changed successfully");
        }
    }

    //called to add a collaborator
    public void addWsCollaborator(User collab,Workspace workspace) {
        Profile profileCollab = createProfile(collab.getId(),workspace.getId());
        DAOProfile.insert(profileCollab);
    }

     ************************************************************/
}