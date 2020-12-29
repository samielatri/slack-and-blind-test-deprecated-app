package database;

import model.group.Message;
import model.group.Workspace;
import model.user.Profile;
import model.user.User;

import java.sql.*;

public class SQLProfileDAO extends AbstractSQLDAO<Profile> {
    Connection conn = ConnectionBuilder.createConnection();
    Statement state = conn.createStatement();
    ResultSet res=null;

    public SQLProfileDAO() throws SQLException {
    }

    @Override
    protected Profile create(ResultSet rs) {
        return null;
    }

    @Override
    protected String getTableName() {
        return null;
    }


    @Override
    public Profile insert(Profile obj) {
        String pf="";
        try{
            res=state.executeQuery("SELECT idProfile FROM profile");
            while (res.next()){
                pf=res.getString("idProfile");
                if(obj.getCompleteName().equals(pf)){
                    System.out.println("This profile is already exist");
                }
            }
            String sql= "INSERT INTO profile (mail, idProfile, currentStatus, completeName, shownName, actualWorkPosition, phoneNumber, timezone) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pstate= conn.prepareStatement(sql);
            //pstate.setString(1,obj.getEmail());
            //pstate.setString(2,obj.getidProfile());
            pstate.setString(3,obj.getCurrentStatus());
            pstate.setString(4,obj.getCompleteName());
            pstate.setString(5,obj.getShownName());
            pstate.setString(6,obj.getActualWorkPosition());
            pstate.setString(7,obj.getPhoneNumber());
            pstate.setString(8,obj.getTimezone());
            res=pstate.executeQuery();
            System.out.println("Profile successfully registred !");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void delete(Profile obj) {

        try{
            String sql= "DELETE FROM profile WHERE idProfile= ?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,obj.getUsername());
            res=pstate.executeQuery();
            System.out.println("Profile deleted successfully");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Profile update(Profile obj) {
        try {
            String sql= "UPDATE profile SET currentStatus = ?, completeName= ?, shownName = ?, actualWorkPosition= ?, phoneNumber= ?, timezone= ? WHERE idProfile= ?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1, obj.getCurrentStatus());
            pstate.setString(2, obj.getCompleteName());
            pstate.setString(3, obj.getShownName());
            pstate.setString(4,obj.getActualWorkPosition());
            pstate.setString(5,obj.getPhoneNumber());
            pstate.setString(6,obj.getTimezone());
            pstate.setString(7,obj.getUsername());
            res=pstate.executeQuery();
            System.out.println("Password updated ! ");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }


    @Override
    public Profile select(String key) throws SQLException { //return a profile
        SQLUserDAO us=new SQLUserDAO();
        SQLWorkspaceDAO wp=new SQLWorkspaceDAO();
        Workspace wks=null;
        User uss=null;
        Profile p=null;
        try{
            String sql= "SELECT * FROM profile WHERE idProfile=?";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,key);
            res=pstate.executeQuery();
            while (res.next()){
                wks= wp.select(res.getString("idWK"));
                uss=us.select(res.getString("mail"));
                p=new Profile(wks,uss);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return p;

    }
}
