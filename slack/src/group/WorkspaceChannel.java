package group;

import userManagment.User;

import java.util.ArrayList;

public class WorkspaceChannel {
    private ArrayList <Message> conversation;
    private String nameCh;
    private User creator;
    private ArrayList<User> adminCh;
    private ArrayList<User> channelMembers;
    private boolean privateCh ;

    public WorkspaceChannel(String nameCh, User creator){
        this.nameCh = nameCh;
        this.creator = creator;
        this.adminCh = new ArrayList<>();
        this.channelMembers = new ArrayList<>();
        this.conversation = new ArrayList<>();
        adminCh.add(creator);
        channelMembers.add(creator);
    }

    public void deleteMessageChannel(String idMessage){
        for (Message messageChannel : conversation){
            if (messageChannel.getIdMessage()==idMessage) {
                conversation.remove(messageChannel);
            }
        }
    }

    @Override
    public String toString() {
        return "#" + nameCh;
    }
    public boolean getPrivateCh(){
        return  privateCh;
    }
    public void setPrivateCh(boolean privateCh){
        this.privateCh = privateCh;
        this.privateCh = privateCh;
    }
    public String getNameCh() {
        return nameCh;
    }
    public ArrayList<User> getAdminChannel(){
        return adminCh;
    }
    public ArrayList<User> getChannelMembers(){
        return channelMembers;
    }
    public void setNameCh(String name){
        this.nameCh = name;
    }

}
