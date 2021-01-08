package database;

import model.user.Profile;

import java.sql.*;

public class SQLUserDAO extends AbstractSQLDAO<Profile> {
    Connection conn = DBConnection.createConnection();
    Statement state = conn.createStatement();
    ResultSet queryResult =null;

    public SQLUserDAO() throws SQLException {
    }

    @Override
    protected Profile create(ResultSet rs) {
        return null;
    }

    @Override
    protected String getTableName() {
        return null;
    }

    /**
     * user connection (sign in)
     * @param profile
     * @return
     */
    public Profile signIn(Profile profile) {
        try {
            String mailDB = ""; // mail retrieved from database
            String passwordDB = ""; // password retrieved from database
            String sqlQuery = "SELECT mail, password FROM user"; // sql query to execute
            queryResult = state.executeQuery(sqlQuery);
            while(queryResult.next()){
                mailDB = queryResult.getString("mail");
                passwordDB = queryResult.getString("password");

                if( profile.getEmail().equals(mailDB) && profile.getPassword().equals(passwordDB) ) {
                    System.out.println("User found in the database !");
                    return profile;
                }
            }
            System.out.println("User not found in the database !");
        } catch(SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * method for insert an user in database
     * @param profile
     * @return
     */
    @Override
    public Profile insert(Profile profile) {

        String mailFromDataBase="";
        String sqlSelectionQuery = "SELECT mail FROM user"; // sql query to execute

        try {

            queryResult = state.executeQuery(sqlSelectionQuery);

            while( queryResult.next() ) {
                mailFromDataBase = queryResult.getString("mail");
                if( profile.getEmail().equalsIgnoreCase(mailFromDataBase) ) {
                    System.out.println( "<DB> Email already exists in the database" ) ;
                    return (Profile) null ;
                }
            }

            String sqlInsertionQuery= "INSERT INTO user (mail, password) VALUES (?,MD5(?))";

            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsertionQuery);
            preparedStatement.setString( 1, profile.getEmail() );
            preparedStatement.setString( 2, profile.getPassword() );

            queryResult = preparedStatement.executeQuery();

            System.out.println("Successfully registred !");
        } catch(SQLException exception) {
            exception.printStackTrace();
        }

        return profile;
    }

    @Override
    public void delete(Profile obj) { //delete an user from the database
        try{
            String sql= "DELETE FROM user WHERE email= ?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,obj.getEmail());
            queryResult =pstate.executeQuery();
            System.out.println("User deleted successfully");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Profile update(Profile obj) {//change password of the user
        try {
            String sql= "UPDATE user SET password = ? WHERE mail= ?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,obj.getEmail());
            pstate.setString(2, obj.getEmail());
            queryResult =pstate.executeQuery();
            System.out.println("Password modified ! ");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Profile(obj.getEmail(),obj.getPassword());
    }

    @Override
    public Profile select(String key) {//select an user in the database
        String uMail="";
        String uPass="";
        Profile u=null;
        try{
            String sql= "SELECT * FROM user WHERE user=?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,key);
            queryResult =state.executeQuery(sql);
            while(queryResult.next()){
                uMail= queryResult.getString(1);
                uPass= queryResult.getString(2);
                u=new Profile(uMail,uPass);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return u;
    }

}
