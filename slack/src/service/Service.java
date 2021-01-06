package service;


import model.group.Message;
import model.group.Workspace;
import model.group.WorkspaceChannel;
import model.user.Profile;
import model.user.User;
import tool.database.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

//import static java.awt.MediaEntry.insert;

public class Service {

    private SlackSystem system;
    private User usr;

    public Workspace createWs(){
        ArrayList<Profile> admins= new ArrayList<>();
        ArrayList<User> members= new ArrayList<>();
        DAO<Workspace> list = DAOFactory.workspace();

        Workspace workspace,ws;
        String wsName;

        Scanner buff;
        System.out.println("Enter the name of the workspace");
        do {
            buff = new Scanner(System.in);
            wsName = buff.nextLine();
            ws = select(wsName);
            if(ws != null){
                System.out.println("This name already exist please enter another one");
            }
        }while(ws != null);//comment le referer au workspaces?
        workspace = new Workspace(wsName);
        ws = insert(workspace);
        admins = select(Profile)//selecttionner tous les profiles qui contiennent l'id du workspace


        /*workspace.getAdmins().add(this.usr);
        workspace.getMembers().add(this.usr);
        usr.getWorkspaces().add(workspace);*/


        return workspace;
    }

    public WorkspaceChannel createCh(Workspace workspace){
        ArrayList<Profile> admins= new ArrayList<>();
        ArrayList<User> members= new ArrayList<>();
        WorkspaceChannel channel;
        String chName;

        Scanner buffer;
        do{
            buffer = new Scanner(System.in);
            chName = buffer.nextLine();
            if(!select(chName)) {
                System.out.println("this channel name already exist, please choose another name");
            }
        }while(!select(chName));
        channel = new WorkspaceChannel(chName);

        /*channel.getAdmins().add(usr);
        channel.getMembers().add(usr);
        channel.setWsId(workspace.getId());*/
        Profile profile = new Profile(user.get);
        admins = select(Profile)//selecttionner tous les profiles qui contiennent l'id du workspace

        //choose if you want it to be private or not
        int choice;
        System.out.println("do you want this channel to be private ?");
        System.out.println("1- yes");
        System.out.println("0- No");
        buffer = new Scanner(System.in);
        choice = buffer.nextInt();
        if(choice == 1) {
            channel.setPrivateCh(true);
            System.out.println("this created channel is private");
        }else{
            channel.setPrivateCh(false);
            channel.getMembers().addAll(workspace.getMembers());
        }
        return channel;
    }

    public void deleteWs(Workspace workspace){
        if(!workspace.getAdmins().contains(usr)){
            System.out.println("you don't have any right on this workspace");
        }else{
            System.out.println("this workspace has been deleted successfully");
            usr.getWorkspaces().remove(workspace);
            system.getWorkspaces().remove(workspace);
        }

    }

    public void deleteCh(WorkspaceChannel channel){
        if(!channel.getAdmins().contains(usr)){
            System.out.println("you don't have any right on this channel");
        }else {
            for(Workspace workspace: system.getWorkspaces()){
                if(workspace.getId().equals(channel.getWsId())){
                    workspace.getWorkspaceChannels().remove(channel);
                    System.out.println("this channel has been deleted successfully");
                    break;
                }
            }
        }
    }

    public void editWs(Workspace workspace){
        Workspace ws;
        String newName;
        Scanner buff;
        if(!workspace.getAdmins().contains(usr)){
            System.out.println("you don't have any right on this workspace");
        }else{
            System.out.println("Enter the new name of this workspace");
            do {
                buff = new Scanner(System.in);
                newName = buff.nextLine();
                ws = new Workspace(newName);
                if(system.getWorkspaces().contains(ws)){
                    System.out.println("This name is taken, please choose another one");
                }
            }while(system.getWorkspaces().contains(ws));
            workspace.setName(newName);
            System.out.println("The workspace name has been changed successfully");
            for(WorkspaceChannel channel: workspace.getWorkspaceChannels()){
                channel.setWsId(newName);
            }
        }
    }


    public void editCh(WorkspaceChannel channel){
        WorkspaceChannel newChannel;
        String newName;
        Scanner buff;
        boolean exist = false;
        if(!channel.getAdmins().contains(usr)){
            System.out.println("you don't have any right on this channel");
        }else{
            System.out.println("Enter the new name of this channel");
            do {
                buff = new Scanner(System.in);
                newName = buff.nextLine();
                newChannel = new WorkspaceChannel(newName);
                for(Workspace workspace: system.getWorkspaces()){
                    if(workspace.getId().equals(channel.getWsId())){
                        if(workspace.getWorkspaceChannels().contains(newChannel)){
                            System.out.println("this channel name already exist, please choose another one");
                            exist = true;
                            break;
                        }
                    }
                }
            }while(exist);
            channel.setName(newName);
            System.out.println("The channel name has been changed successfully");

        }
    }

    public void sendChannelMsg(WorkspaceChannel channel,Profile profile){
        Message message;
        Object o = null;//quel est l'objet canal??
        String content;
        Scanner buffer;
        Date date = new Date();

        System.out.println("Enter the content of your message");
        buffer = new Scanner(System.in);
        content = buffer.next();
        message = new Message(profile,content,o);
        message.setCreatedAt(date);
        channel.getConversation().add(message);
    }

    public void deleteChannelMsg(Message msg,WorkspaceChannel channel){
        if(channel.getConversation().isEmpty()){
            System.out.println("This conversation is already empty");
        }else{
            channel.getConversation().remove(msg);
        }
    }

    public void editChannelMsg(Message msg){
        String newContent;
        Scanner buffer;
        Date date = new Date();
        System.out.println("Enter the new content of your message");
        buffer = new Scanner(System.in);
        newContent = buffer.next();
        msg.setCreatedAt(date);

        msg.setContent(newContent);
        System.out.println("your content has been changed successfully");
    }
}
