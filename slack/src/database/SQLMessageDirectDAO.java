package database;

import model.group.Message;
import model.user.Profile;

import java.sql.*;

public class SQLMessageDirectDAO extends AbstractSQLDAO<Message> {
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
    public Message insert(Message obj) { //add message in the current workspace (database)

        try{
            String first= "SELECT nameWK FROM workspace WHERE idProfile=?";
            PreparedStatement pstate= conn.prepareStatement(first);
            pstate.setString(1,obj.getSenderMessage());
            res=pstate.executeQuery();
            String nameWKDB=res.getString("nameWK");
            String sql= "INSERT INTO messagedirect (idMsg, idProfile,nameWK, contenu, createDate,updateDate) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,obj.getId());
            pstate.setString(2,obj.getSenderMessage());
            pstate.setString(3,nameWKDB);
            pstate.setString(4,obj.getContent());
            pstate.setObject(5,obj.getCreatedAt());
            pstate.setObject(6,obj.getUpdatedAt());
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
            String sql= "DELETE FROM messagedirect WHERE idMsg= ?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,obj.getId());
            res=pstate.executeQuery();
            System.out.println("Message deleted");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Message update(Message obj) { //modify the message's content
        try {
            String sql= "UPDATE messagedirect SET contenu = ?, updateDate= ? WHERE idMsg= ?";
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
    public Message select(String key) { //return all the message from the workspace
        Message msgC=null;
        String sender="";
        String content="";
        try{
            String sql= "SELECT sender, contenu FROM messagedirect WHERE nameWK=?";
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
