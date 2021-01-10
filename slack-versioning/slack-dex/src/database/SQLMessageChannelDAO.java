package database;

import model.communication.Message;
import model.communication.Workspace;
import model.user.Profile;
import model.SlackSystem;

import java.sql.*;
import java.util.ArrayList;

public class SQLMessageChannelDAO extends AbstractSQLDAO<Message> {
    Connection conn = DBConnection.createConnection();
    Statement state = conn.createStatement();
    ResultSet queryResult = null;

    private SlackSystem system=new SlackSystem();

    public SQLMessageChannelDAO() throws SQLException {
    }

    @Override
    protected Message create(ResultSet rs) throws SQLException {
        return new Message(system.getCurrentConnectedProfile(), rs.getString("msgContent"));
    }

    @Override
    protected String getTableName() {
        return "messagechannel";
    }

    @Override
    public Message insert(Message message) { //add the message in the channel (database)

        try {
            String sql= "INSERT INTO messageChannel (idMsg, nameWC, msgContent, createDate,updateDate, sender) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString( 1, message.getId() );
            preparedStatement.setString( 2,system.getCurrentConnectedWorkspaceChannel().getName() );
            preparedStatement.setString( 3, message.getContent() );
            preparedStatement.setObject( 4, message.getCreatedAt() );
            preparedStatement.setObject( 5, message.getUpdatedAt() );
            preparedStatement.setString( 6, system.getCurrentConnectedProfile().getUsername());
            queryResult = preparedStatement.executeQuery();
            System.out.println("Message added !");
        } catch( SQLException exception ) {
            exception.printStackTrace();
        }
        return message;
    }


    @Override
    public void delete( Message obj ) { //delete the message
        try {
            String sql= "DELETE FROM messagechannel WHERE idMsg= ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,obj.getId());
            queryResult = preparedStatement.executeQuery();
            System.out.println("Message deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Message update(Message obj) { //modifiy the message's content

        try {
            String sql= "UPDATE messageChannel SET msgContent = ?, updateDate= ? WHERE idMsg= ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString( 1, obj.getContent() );
            preparedStatement.setObject( 2, obj.getUpdatedAt() );
            preparedStatement.setString( 3,obj.getId() );
            queryResult = preparedStatement.executeQuery();
            System.out.println("Message modified ! ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;

    }

    @Override
    public Message select(String key) throws SQLException { //return a message

        Message message = null;
        SQLProfileDAO sp = new SQLProfileDAO();
        Profile p = null;
        try{
            String sql= "SELECT * FROM messageChannel WHERE idMsg=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, key);
            queryResult = preparedStatement.executeQuery();
            while (queryResult.next()){
                p = sp.select(queryResult.getString("idProfile"));
                message = new Message(p, queryResult.getString("msgContent"));
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return message;

    }

    /*public ArrayList<Message> selectAll(){
        ArrayList<Message> listMessages=new ArrayList<>();
        Message m=null;
        try {
            String sql="SELECT * FROM messageChannel";
            queryResult=state.executeQuery(sql);
            while (queryResult.next()){
                m=new Message(system.getCurrentConnectedProfile(),queryResult.getString("msgContent"));
                listMessages.add(m);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listMessages;
    }*/



}
