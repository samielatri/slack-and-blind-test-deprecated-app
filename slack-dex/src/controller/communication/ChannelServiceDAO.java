package src.controller.communication;

import controller.AbstractServiceDAO;

public class ChannelServiceDAO extends AbstractServiceDAO {


    /*********************************************************



    //function called by a profile
    public WorkspaceChannel createCh(Workspace workspace) throws SQLException {
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
        String id = currentConnectedUser.getId()+"."+workspace.getId();
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
            channel.setPrivate(1);
            System.out.println("this created channel is private");
        }else{
            channel.setPrivate(0);
        }

        ch = DAOChannel.insert(channel);
        if(ch!=null){
            System.out.println("this channel has been created succefully");
        }else{
            System.out.println("this channel hasn't been created ! please try again");
        }

        return channel;
    }


    //called by a profile
    public void quitCh(WorkspaceChannel channel){
        //we actually can't quit a channel
    }


    //called by a profile
    public void deleteCh(WorkspaceChannel channel){
        ArrayList<Profile> chProfiles = new ArrayList<Profile>();

        if(connectedProfile.getIsAdminCh() == 0){
            System.out.println("you don't have any right on this channel");
        }else {
            //take the role of admin from the profiles that are admins on this channel
            chProfiles = (ArrayList<Profile>) DAOProfile.selectAll();
            for(Profile profile : chProfiles){
                if(profile.isAdminCh()==1){
                    profile.setIsAdminCh(0);
                }
            }
            //delete the channel
            DAOChannel.delete(channel);
            System.out.println("this channel has been deleted successfully");
        }
    }

    public void editCh(WorkspaceChannel channel) throws SQLException {//called by a profile
        String newName;
        Scanner buff;
        WorkspaceChannel wsChannel;

        boolean exist = false;
        if(connectedProfile.getIsAdminCh()==0){
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
        message = new Message(connectedProfile,content);
        message.setCreatedAt(date);
        message.setIdCh(channel.getId());
        //channel.getConversation().add(message);
        return DAOMessageChannel.insert(message);
    }

    public void deleteChannelMsg(Message msg){//called by a profile
        if(msg.getIdSenderMessage()== connectedProfile.getId()){
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


     **********************************************************/
}
