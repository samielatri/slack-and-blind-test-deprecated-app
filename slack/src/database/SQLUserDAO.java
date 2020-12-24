package database;

import model.user.User;
import java.sql.*;

public class SQLUserDAO extends AbstractSQLDAO<User> {
    Connection conn = ConnectionBuilder.createConnection();
    Statement state = conn.createStatement();
    ResultSet res=null;

    @Override
    protected User create(ResultSet rs) {
        return null;
    }

    @Override
    protected String getTableName() {
        return null;
    }

    public User signIn(String mail, String password){ //method for user connection (sign in)
        String mailDB="";
        String passwordDB="";
        try{
            res=state.executeQuery("SELECT mail, password FROM user");
            while(res.next()){
                mailDB=res.getString("mail");
                passwordDB=res.getString("password");

                if(mail.equals(mailDB) && password.equals(passwordDB)){
                    System.out.println("User connected!");
                }
            }
            System.out.println("User not found");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new User(mail,password);
    }

    @Override
    public User insert(String email, String password) { //method for insert an user in database (sign up)
        String mailDB="";
        try{
            res=state.executeQuery("SELECT mail FROM user");
            while(res.next()){
                mailDB=res.getString("mail");
                if(email.equals(mailDB)){
                    System.out.println("Email already exists, please enter another one");
                }
            }
            String sql= "INSERT INTO user (mail, password) VALUES (?,MD5(?))";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,email);
            pstate.setString(2,password);
            res=pstate.executeQuery();
            System.out.println("Successfully registred !");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new User(email,password);
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
    public User update(User obj, String password) { //change password of the user
        try {
            String sql= "UPDATE user SET password = ? WHERE mail= ?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,password);
            pstate.setString(2, obj.getEmail());
            res=pstate.executeQuery();
            System.out.println("Password modified ! ");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new User(obj.getEmail(),password);
    }

    @Override
    public User select() { //return all user in the database
        String uMail="";
        String uPass="";
        User u=null;
        try{
            String sql= "SELECT * FROM user";
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
