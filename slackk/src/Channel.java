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

    @Override
    public String toString() {
        return "Channel " + nomCh;
    }
}
