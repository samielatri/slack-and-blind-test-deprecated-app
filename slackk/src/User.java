import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class User {
    private ArrayList<Channel> createdChannels; // list of all channels created by the user
    private ArrayList<Channel> joinedChannels; // list of all channels joined by the user
    private ArrayList<Message> sentMessages; // list of all messages sent by the user
    private ArrayList<User> friendList; // COLLABORATEURS
    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username, String password){
        this.username=username;
        this.password = password;
        this.createdChannels=new ArrayList<>();
        this.joinedChannels= new ArrayList<>();
        this.sentMessages=new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Channel> getCreatedChannels() {
        return createdChannels;
    }
// workspace
    // liste collabateur
        //sin
    // liste user
        //sin
            //
    public Message sendMsg(){
        String msg;
        Date date = new Date();
        Scanner buffer = new Scanner(System.in);
        msg = buffer.next();
        //addCollabteur(user);
        return new Message(this, user, msg,date, this.username);
    }
    public Channel createCh(){
        String nom;
        Scanner buffer;
        do{
            buffer = new Scanner(System.in);
            nom = buffer.next();
            Channel chan = new Channel(nom,this);
            if(createdChannels.contains(chan)){
                System.out.println("this channel already exist");
            }
        }while(createdChannels.contains(chan));
        createdChannels.add(chan);
        return chan;
    }
    public void deleteCh(){
        if(createdChannels.isEmpty()){
            System.out.println("you don't have any right on channel, call jesus");
        }else{
            System.out.println("please enter the name of the channel to delete :");
            Scanner buffer = new Scanner(System.in);
            String nom = buffer.next();
            Channel c=new Channel(nom,this);
            if(createdChannels.contains(c)){
                createdChannels.remove(c);
                System.out.println("this channel has been deleted successfully");
            }else{
                System.out.println("this channel doesn't exist");
            }

        }
    }
    public void deleteMsg(){
        if(sentMessages.isEmpty()){
            System.out.println("you don't have any message");
        }else{
            System.out.println("please enter the id of the message :");
            Scanner buffer = new Scanner(System.in);
            int idmsg = buffer.nextInt();
            if(idmsg>=0 && idmsg<sentMessages.size()){
                Message m=sentMessages.get(idmsg);
                sentMessages.remove(m);
                System.out.println("this message has been deleted successfully");
            }else{
                System.out.println("Message not found");
            }

        }
    }
    public void editMsg(){
        if(sentMessages.isEmpty()){
            System.out.println("you don't have any message");
        }else{
            System.out.println("please enter the id of the message you want edit :");
            Scanner buffer = new Scanner(System.in);
            int idmsg = buffer.nextInt();
            if(idmsg>=0 && idmsg<sentMessages.size()){
                Message m=sentMessages.get(idmsg);
                System.out.println("Edit your message here: ");
                String edit=buffer.next();
                m= new Message(edit,m.getCreatedAt(),new Date(), m.username);
                System.out.println("this message has been deleted successfully");
            }else{
                System.out.println("Message not found");
            }

        }
    }

    public void addFriend(User user){
        friendList.add(user);
    }

    public void inviterFriend(User user){
        if (joinedChannels.contains(user)) {
            System.out.println(this.toString() + " envoie une demande d'amis à " + user.toString() + " dans le serveur " + joinedChannels.toString());
        } else {
            System.out.println("Utilisateur inexistant dans le channel actuel");
        }
    }
    public void inviteChannel(){

    }
    public void leaveChannel(){

    }
    public void deleteFriend(){

    }


    @Override
    public String toString() {
        return "Bienvenue" + username;
    }


}
