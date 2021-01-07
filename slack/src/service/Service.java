package service;

import model.group.Conversation;
import model.group.Message;
import model.group.Workspace;
import model.group.WorkspaceChannel;
import model.user.Profile;
import model.user.User;
import database.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

//import static java.awt.MediaEntry.insert;

public class Service {
    DAO<WorkspaceChannel> DAOChannel = DAOFactory.workspaceChannel();
    DAO<Workspace> DAOWorkspace = DAOFactory.workspace();
    DAO<Profile> DAOProfile = DAOFactory.profile();
    DAO<Message> DAOMessageDirect = DAOFactory.messageDirect();
    DAO<Message> DAOMessageChannel = DAOFactory.messageChannel();

    private User usr;

    public Workspace createWs() throws SQLException {//function called by a user
        Workspace workspace,ws;

        String wsName;
        Scanner buff;

        System.out.println("Enter the name of the workspace");
        do {
            buff = new Scanner(System.in);
            wsName = buff.nextLine();
            ws = DAOWorkspace.select(wsName);
            if(ws != null){
                System.out.println("This name already exist please enter another one");
            }
        }while(ws != null);

        workspace = new Workspace(wsName);
        ws = DAOWorkspace.insert(workspace);
        if(ws!=null){
            System.out.println("this workspace has been created succefully");
        }else{
            System.out.println("this workspace hasn't been created ! please try again");
        }
        //create a profile for the user who's creating the workspace
        Profile profile;
        profile = createProfile(usr.getId(),workspace.getId());

        //put the creator as an admin
        profile.setIsAdminWS(1);
        DAOProfile.update(profile);

        return workspace;
    }

    public WorkspaceChannel createCh(Workspace workspace) throws SQLException {//function called by a profile
        WorkspaceChannel channel,ch;
        String chName;
        Scanner buffer;

        System.out.println("Enter the name of the channel");
        do{
            buffer = new Scanner(System.in);
            chName = buffer.nextLine();
            ch = DAOChannel.select(chName);
            if(ch != null) {
                System.out.println("this channel name already exist, please choose another name");
            }
        }while(ch != null);
        channel = new WorkspaceChannel(chName);
        channel.setWsId(workspace.getId());

        //putting the profile that created it as an admin (to change !)
        String id = usr.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(id);
        profile.setIsAdminCh(1);
        DAOProfile.update(profile);

        //choose if you want it to be private or not
        int choice;
        System.out.println("do you want this channel to be private ?");
        System.out.println("1- yes");
        System.out.println("0- No");
        buffer = new Scanner(System.in);
        choice = buffer.nextInt();
        if(choice == 1) {
            channel.setPrivateCh(1);
            System.out.println("this created channel is private");
        }else{
            channel.setPrivateCh(0);
        }

        ch = DAOChannel.insert(channel);
        if(ch!=null){
            System.out.println("this channel has been created succefully");
        }else{
            System.out.println("this channel hasn't been created ! please try again");
        }

        return channel;
    }

    public void quitWs(Workspace workspace) throws SQLException {//called by a user
        String id = usr.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(id);
        DAOProfile.delete(profile);
    }

    public void quitCh(WorkspaceChannel channel){//called by a profile
        //we actually can't quit a channel
    }


    public void deleteWs(Workspace workspace) throws SQLException {//called by a user
        ArrayList<WorkspaceChannel> wsChannel = new ArrayList<WorkspaceChannel>();
        ArrayList<Profile> wsProfiles = new ArrayList<Profile>();

        String idProfile = usr.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(idProfile);

        if(profile.getIsAdminWS()==1){
            //delete all channels of this workspace
            wsChannel = (ArrayList<WorkspaceChannel>) DAOChannel.selectAll();
            for(WorkspaceChannel channel : wsChannel){
                if(channel.getWsId()==workspace.getId()){
                    DAOChannel.delete(channel);
                }
            }
            wsProfiles = (ArrayList<Profile>) DAOProfile.selectAll();
            //delete all it's profiles
            for(Profile p : wsProfiles){
                if(p.getIdWs()==workspace.getId()){
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

    public void deleteCh(WorkspaceChannel channel){//called by a profile
        ArrayList<Profile> chProfiles = new ArrayList<Profile>();

        if(this.getAdminCH()==0){
            System.out.println("you don't have any right on this channel");
        }else {
            //take the role of admin from the profiles that are admins on this channel
            chProfiles = (ArrayList<Profile>) DAOProfile.selectAll();
            for(Profile profile : chProfiles){
                if(profile.getIsAdminCh()==1){
                    profile.setIsAdminCh(0);
                }
            }
            //delete the channel
            DAOChannel.delete(channel);
            System.out.println("this channel has been deleted successfully");
        }
    }

    public void editWs(Workspace workspace) throws SQLException {//called by a user
        Workspace ws;
        ArrayList<Profile> wsProfiles = new ArrayList<Profile>();
        ArrayList<WorkspaceChannel> wsChannels = new ArrayList<WorkspaceChannel>();
        String newName;
        boolean exist = false;
        String idProfile = usr.getId()+"."+workspace.getId();
        Profile profile = DAOProfile.select(idProfile);

        Scanner buff;
        if(profile.getIsAdminWS()==0){
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
                if(p.getIdWs()==workspace.getId()){
                    p.setIdWs(newName);
                    DAOProfile.update(p);
                }
            }
            //update the workspace
            workspace.setName(newName);
            ws = DAOWorkspace.update(workspace);
            System.out.println("The workspace informations has been changed successfully");
        }
    }


    public void editCh(WorkspaceChannel channel) throws SQLException {//called by a profile
        String newName;
        Scanner buff;
        WorkspaceChannel wsChannel;

        boolean exist = false;
        if(this.getIsAdminCH()==0){
            System.out.println("you don't have any right on this channel");
        }else{
            System.out.println("Enter the new name of this channel");
            do {
                buff = new Scanner(System.in);
                newName = buff.nextLine();
                wsChannel = DAOChannel.select(newName);
                if(channel!=null) {
                    exist=true;
                    System.out.println("this channel name already exist, please choose another one");
                }

            }while(exist);
            //updating the id channel for all the messages
            channel.setName(newName);
            wsChannel = DAOChannel.update(channel);
            System.out.println("The channel informations has been changed successfully");

        }
    }

    public Message sendChannelMsg(WorkspaceChannel channel){//called by a profile
        Message message;
        String content;
        Scanner buffer;
        Date date = new Date();

        System.out.println("Enter the content of your message");
        buffer = new Scanner(System.in);
        content = buffer.next();
        message = new Message(this,content);
        message.setCreatedAt(date);
        message.setIdCh(channel.getId());
        //channel.getConversation().add(message);
        return DAOMessageChannel.insert(message);
    }

    public void deleteChannelMsg(Message msg){//called by a profile
        if(msg.getIdSenderMessage()==this.getId()){
            DAOMessageChannel.delete(msg);
            System.out.println("the message has been deleted succefully");
        }else{
            System.out.println("The message that you are trying to delete is not yours, please select a message that has been sent by you");
        }
    }

    public Message editChannelMsg(Message msg){
        Message message;
        String newContent;
        Scanner buffer;
        Date date = new Date();
        System.out.println("Enter the new content of your message");
        buffer = new Scanner(System.in);
        newContent = buffer.next();
        msg.setCreatedAt(date);

        msg.setContent(newContent);
        message = DAOMessageChannel.update(msg);
        System.out.println("your content has been changed successfully");
        return message;
    }
    public Conversation createConversation(){
        Conversation conversation;
        String name;
        Scanner buffer;
        Date date = new Date();
        buffer = new Scanner(System.in);
        name = buffer.nextLine();
        conversation = new Conversation(name,date);
        return conversation;
    }
    public Message sendConversationMsg(Conversation conversation){
        Message message;
        String content;
        Scanner buffer;
        Date date = new Date();

        System.out.println("Enter the content of your message");
        buffer = new Scanner(System.in);
        content = buffer.next();
        message = new Message(this,content);
        message.setCreatedAt(date);
        message.setIdConversation(conversation.getId());
        return DAOMessageDirect.insert(message);
    }
    public Message editConversationMsg(Message msg){
        Message message;
        String newContent;
        Scanner buffer;
        Date date = new Date();
        System.out.println("Enter the new content of your message");
        buffer = new Scanner(System.in);
        newContent = buffer.next();
        msg.setCreatedAt(date);

        msg.setContent(newContent);
        message = DAOMessageDirect.update(msg);
        System.out.println("your content has been changed successfully");
        return message;
    }

    public void addWsCollaborator(User collab,Workspace workspace) {//called to add a collaborator
        Profile profileCollab = createProfile(collab.getId(),workspace.getId());
        DAOProfile.insert(profileCollab);
    }

    public void addChCollaborator(Profile collab) {//called by a user to add a collaborator
        //How to add a collab in a channel ?
        //How can we know that a profile /user is in a "X" channel
    }

    public Profile createProfile(String idUsr,String idWs){//called by a user
        Profile profile = new Profile(idWs,idUsr);
        String id = idUsr+"."+idWs;
        profile.setId(id);
        return profile;
    }
}


