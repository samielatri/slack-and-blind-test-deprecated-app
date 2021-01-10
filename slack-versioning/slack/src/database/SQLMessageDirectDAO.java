package database;

import model.SlackSystem;
import model.communication.Message;
import model.user.Profile;

import java.sql.*;
import java.util.ArrayList;

public class SQLMessageDirectDAO extends AbstractSQLDAO<Message> {
    Connection conn = DBConnection.createConnection();
    Statement state = conn.createStatement();
    ResultSet res = null;
    private SlackSystem system=new SlackSystem();

    public SQLMessageDirectDAO() throws SQLException {
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
    public Message insert(Message obj) { //add message in the current workspace (database)

        try{
            String first= "SELECT * FROM workspace WHERE idProfile=?";
            PreparedStatement pstate = conn.prepareStatement(first);
            pstate.setString(1, system.getCurrentConnectedProfile().getId());
            res=pstate.executeQuery();
            String nameWKDB=res.getString("nameWK");
            String sql= "INSERT INTO messageDirect (idMsg, idProfile,nameWK, msgContent, createDate,updateDate) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstate2= conn.prepareStatement(sql);
            pstate2.setString(1,obj.getId());
            pstate2.setString(2,system.getCurrentConnectedProfile().getId());
            pstate2.setString(3,nameWKDB);
            pstate2.setString(4,obj.getContent());
            pstate2.setObject(5,obj.getCreatedAt());
            pstate2.setObject(6,obj.getUpdatedAt());
            res=pstate2.executeQuery();
            System.out.println("Message added !");
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        return obj;
    }

    @Override
    public void delete(Message obj) { //delete the message
        try{
            String sql= "DELETE FROM messageDirect WHERE idMsg= ?";
            PreparedStatement pstate = conn.prepareStatement(sql);
            pstate.setString(1,obj.getId());
            res = pstate.executeQuery();
            System.out.println("Message deleted");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Message update(Message obj) { //modify the message's content
        try {
            String sql= "UPDATE messageDirect SET msgContent = ?, updateDate= ? WHERE idMsg= ?";
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
    public Message select(String key) throws SQLException { //return a message from the workspace
        Message msgC=null;
        SQLProfileDAO sp=new SQLProfileDAO();
        Profile p=null;
        try{
            String sql= "SELECT * FROM messageDirect WHERE idMsg=?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,key);
            res = pstate.executeQuery();
            while (res.next()){
                p=sp.select(res.getString("idProfile"));
                msgC=new Message(p,res.getString("msgContent"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return msgC;
    }

    /*public ArrayList<Message> selectAll(){
        ArrayList<Message> listMessages=new ArrayList<>();
        Message m=null;
        try {
            String sql="SELECT * FROM messageDirect";
            res=state.executeQuery(sql);
            while (res.next()){
                m=new Message(system.getCurrentConnectedProfile(),res.getString("msgContent"));
                listMessages.add(m);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listMessages;
    }*/
}
