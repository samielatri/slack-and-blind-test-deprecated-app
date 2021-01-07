package database;

import model.communication.Message;
import model.user.Profile;

import java.sql.*;

public class SQLMessageChannelDAO extends AbstractSQLDAO<Message> {
    Connection conn = DBConnection.createConnection();
    Statement state = conn.createStatement();
    ResultSet res=null;

    public SQLMessageChannelDAO() throws SQLException {
    }

    @Override
    protected Message create(ResultSet rs) {
        return null;
    }

    @Override
    protected String getTableName() {
        return null;
    }

    @Override
    public Message insert(Message message) { //add the message in the channel (database)
        try{

            String sql= "INSERT INTO messagechannel (idMsg, nameWC, contenu, createDate,updateDate, sender) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,message.getId());
            pstate.setString(2,message.getWorkspaceChannel().getName());
            pstate.setString(3,message.getContent());
            pstate.setObject(4,message.getCreatedAt());
            pstate.setObject(5,message.getUpdatedAt());
            pstate.setString(6,message.getSenderMessage());
            res=pstate.executeQuery();
            System.out.println("Message added !");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return message;
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
        return obj;
    }

    @Override
    public Message select(String key) throws SQLException { //return a message
        Message msgC=null;
        SQLProfileDAO sp=new SQLProfileDAO();
        Profile p=null;
        try{
            String sql= "SELECT * FROM messagechannel WHERE idMsg=?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,key);
            res=pstate.executeQuery();
            while (res.next()){
                p=sp.select(res.getString("idProfile"));
                msgC=new Message(p,res.getString("msgContent"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return msgC;

    }
}
