package database;

import model.user.Profile;
import model.user.User;
import java.sql.*;
import java.util.ArrayList;

public class SQLUserDAO extends AbstractSQLDAO<User> {
    Connection conn = DBConnection.createConnection();
    Statement state = conn.createStatement();
    ResultSet queryResult =null;
    int res;

    public SQLUserDAO() throws SQLException {
    }

    @Override
    protected User create(ResultSet rs) throws SQLException {
        return new User(rs.getString("mail"),rs.getString("password"));
    }

    @Override
    protected String getTableName() {
        System.out.println("table user");
        return "user";
    }

    /**
     * user connection (sign in)
     * @param user
     * @return
     */
    public User signIn(User user) {
        try {
            String mailDB = ""; // mail retrieved from database
            String passwordDB = ""; // password retrieved from database
            String sqlQuery = "SELECT mail, password FROM user"; // sql query to execute
            queryResult = state.executeQuery(sqlQuery);
            while(queryResult.next()){
                mailDB = queryResult.getString("mail");
                passwordDB = queryResult.getString("password");

                if( user.getEmail().equals(mailDB) && user.getPassword().equals(passwordDB) ) {
                    System.out.println("User found in the database !");
                    return user ;
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
     * @param user
     * @return
     */
    @Override
    public User insert(User user) {
        System.out.println("INSERT DB");
        String mailFromDataBase="";
        String sqlSelectionQuery = "SELECT mail FROM user"; // sql query to execute

        try {

            queryResult = state.executeQuery(sqlSelectionQuery);

            while( queryResult.next() ) {
                mailFromDataBase = queryResult.getString("mail");
                if( user.getEmail().equalsIgnoreCase(mailFromDataBase) ) {
                    System.out.println( "<DB> Email already exists in the database" ) ;
                    return null ;
                }
            }

            String sqlInsertionQuery= "INSERT INTO user (mail, password) VALUES (?,MD5(?))";

            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsertionQuery);
            preparedStatement.setString( 1,user.getEmail() );
            preparedStatement.setString( 2, user.getPassword() );

            res = preparedStatement.executeUpdate();

            System.out.println("<DB> Successfully registred !");
        } catch(SQLException exception) {
            exception.printStackTrace();
        } finally {
            System.out.println("<DB> SQL problem");
        }

        return user;
    }

    @Override
    public void delete(User obj) { //delete an user from the database
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
    public User update(User obj) {//change password of the user
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
        return new User(obj.getEmail(),obj.getPassword());
    }

    @Override
    public User select(String key) {//select an user in the database
        String uMail="";
        String uPass="";
        User u=null;
        try{
            String sql= "SELECT * FROM user WHERE user=?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,key);
            queryResult =state.executeQuery(sql);
            while(queryResult.next()){
                uMail= queryResult.getString(1);
                uPass= queryResult.getString(2);
                u=new User(uMail,uPass);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return u;
    }

    /*public ArrayList<User> selectAll(){
        ArrayList<User> listUser=new ArrayList<>();
        User u=null;
        try {
            String sql="SELECT * FROM user";
            queryResult=state.executeQuery(sql);
            while (queryResult.next()){
                u=new User(queryResult.getString("mail"),queryResult.getString("password"));
                listUser.add(u);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listUser;
    }*/

}
