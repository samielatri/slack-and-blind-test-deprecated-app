package database;

import model.user.User;
import java.sql.*;

public class SQLUserDAO extends AbstractSQLDAO<User> {
    Connection conn = DBConnection.createConnection();
    Statement state = conn.createStatement();
    ResultSet res=null;

    public SQLUserDAO() throws SQLException {
    }

    @Override
    protected User create(ResultSet rs) {
        return null;
    }

    @Override
    protected String getTableName() {
        return null;
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
            res = state.executeQuery(sqlQuery);
            while(res.next()){
                mailDB = res.getString("mail");
                passwordDB = res.getString("password");

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
     * method for insert an user in database (sign up)
     * @param user
     * @return
     */
    @Override
    public User insert(User user) {
        String mailDB="";
        String sqlQuery = "SELECT mail FROM user"; // sql query to execute
        try{
            res=state.executeQuery(sqlQuery);
            while(res.next()){
                mailDB=res.getString("mail");
                if(user.getEmail().equals(mailDB)){
                    System.out.println("Email already exists, please enter another one");
                }
            }
            String sql= "INSERT INTO user (mail, password) VALUES (?,MD5(?))";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,user.getEmail());
            pstate.setString(2, user.getPassword());
            res=pstate.executeQuery();
            System.out.println("Successfully registred !");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new User(user.getEmail(),user.getPassword());
    }

    @Override
    public void delete(User obj) { //delete an user from the database
        try{
            String sql= "DELETE FROM user WHERE email= ?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,obj.getEmail());
            res=pstate.executeQuery();
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
            res=pstate.executeQuery();
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
            res=state.executeQuery(sql);
            while(res.next()){
                uMail=res.getString(1);
                uPass=res.getString(2);
                u=new User(uMail,uPass);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return u;
    }

}
