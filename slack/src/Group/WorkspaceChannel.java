package Group;

import UserManagment.User;

import java.util.ArrayList;

public class WorkspaceChannel {
    private ArrayList <Message> msg;
    private String nomCh;

    private User adminCh;
    private User adminWorkspace;

    public WorkspaceChannel(String nomCh, User adminCh){
        this.nomCh = nomCh;
        this.adminCh = adminCh;
    }

    public void deleteMessageChannel(String idMessage){
        for (Message messageChannel : msg){
            if (messageChannel.getIdMessage()==idMessage) {
                msg.remove(messageChannel);
            }
        }
    }

    @Override
    public String toString() {
        return "Group.Channel " + nomCh;
    }

    public String getChannelName() {
        return nomCh;
    }
    public User getAdminChannel(){
        return adminCh;
    }
    public User getAdminWorkspace(){
        return adminWorkspace;
    }

}
