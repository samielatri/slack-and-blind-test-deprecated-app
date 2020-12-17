package Group;

import UserManagment.User;

import java.util.ArrayList;

public class WorkspaceChannel {
    private ArrayList <Message> conversation;
    private String nameCh;
    private ArrayList<User> adminCh;

    public WorkspaceChannel(String nameCh, ArrayList<User> adminCh){
        this.nameCh = nameCh;
        this.adminCh = adminCh;
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
        return "Group.Channel " + nameCh;
    }

    public String getChannelName() {
        return nameCh;
    }
    public User getAdminChannel(){
        return adminCh;
    }
    public User getAdminWorkspace(){
        return adminWorkspace;
    }

}
