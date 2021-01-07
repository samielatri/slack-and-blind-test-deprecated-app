package database;

import model.communication.Message;
import model.user.Profile;

import java.sql.*;

public class SQLMessageChannelDAO extends AbstractSQLDAO<Message> {
    Connection conn = DBConnection.createConnection();
    Statement state = conn.createStatement();
    ResultSet queryResult = null;

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
        try {
            String sql= "INSERT INTO messagechannel (idMsg, nameWC, contenu, createDate,updateDate, sender) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString( 1, message.getId() );
            preparedStatement.setString( 2, message.getWorkspaceChannel().getName() );
            preparedStatement.setString( 3, message.getContent() );
            preparedStatement.setObject( 4, message.getCreatedAt() );
            preparedStatement.setObject( 5, message.getUpdatedAt() );
            preparedStatement.setString( 6, message.getSenderMessage() );
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
            String sql= "UPDATE messagechannel SET contenu = ?, updateDate= ? WHERE idMsg= ?";
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
            String sql= "SELECT * FROM messagechannel WHERE idMsg=?";
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

}
