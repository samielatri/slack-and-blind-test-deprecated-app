import java.util.ArrayList;

public class Channel {
    private ArrayList <Message> msg;
    private String nomCh;

    private User adminCh;
    private User adminWorkspace;

    public Channel(String nomCh,User adminCh){
        this.nomCh=nomCh;
        this.adminCh=adminCh;
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
        return "Channel " + nomCh;
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
