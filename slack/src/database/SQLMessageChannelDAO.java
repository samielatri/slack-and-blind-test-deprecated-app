package database;

import model.group.Message;

import java.sql.*;

public class SQLMessageChannelDAO extends AbstractSQLDAO<Message> {
    Connection conn = ConnectionBuilder.createConnection();
    Statement state = conn.createStatement();
    ResultSet res=null;
    @Override
    protected Message create(ResultSet rs) {
        return null;
    }

    @Override
    protected String getTableName() {
        return null;
    }

    @Override
    public Message insert(Message obj) { //add the message in the channel (database)
        try{
            String sql= "INSERT INTO messagechannel (idMsg, nameWC, contenu, createDate,updateDate, sender) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,obj.getId());
            pstate.setString(2,obj.getWorkspaceChannel().getName());
            pstate.setString(3,obj.getContent());
            pstate.setObject(4,obj.getCreatedAt());
            pstate.setObject(5,obj.getUpdatedAt());
            pstate.setString(6,obj.getSenderMessage());
            res=pstate.executeQuery();
            System.out.println("Message added !");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new Message(obj.getSenderMessage(),obj.getContent(),this);
    }

    @Override
    public void delete(Message obj) { //delete the message
        try{
            String sql= "DELETE FROM messagechannel WHERE idMsg= ?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,obj.getId());
            res=pstate.executeQuery();
            System.out.println("Message deleted");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Message update(Message obj) { //modifiy the message's content
        try {
            String sql= "UPDATE messagechannel SET contenu = ?, updateDate= ? WHERE idMsg= ?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1, obj.getContent());
            pstate.setObject(2, obj.getUpdatedAt());
            pstate.setString(3,obj.getId());
            res=pstate.executeQuery();
            System.out.println("Message modified ! ");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Message(obj.getSenderMessage(),obj.getContent(),this);
    }

    @Override
    public Message select(String key) { //return all the message in the channel
        Message msgC=null;
        String sender="";
        String content="";
        try{
            String sql= "SELECT sender, contenu FROM messagechannel WHERE nameWC=?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,key);
            res=pstate.executeQuery();
            while (res.next()){
                sender=res.getString("sender");
                content=res.getString("contenu");
                msgC=new Message(sender,content,this);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return msgC;
    }
}
