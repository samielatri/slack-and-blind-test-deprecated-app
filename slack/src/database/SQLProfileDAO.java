package database;

import model.group.Workspace;
import model.user.Profile;
import model.user.User;

import java.sql.*;

public class SQLProfileDAO extends AbstractSQLDAO<Profile> {
    Connection conn = ConnectionBuilder.createConnection();
    Statement state = conn.createStatement();
    ResultSet res=null;

    @Override
    protected Profile create(ResultSet rs) {
        return null;
    }

    @Override
    protected String getTableName() {
        return null;
    }

    @Override
    public Profile insert(User obj, String nameProfile, String currentStatus,
                          String completeName,String shownName,String actualWorkPosition,String phoneNumber,String timezone) {
        String pf="";
        try{
            res=state.executeQuery("SELECT idProfile FROM profile");
            while (res.next()){
                pf=res.getString("idProfile");
                if(nameProfile.equals(pf)){
                    System.out.println("This profile is already exist");
                }
            }
            String sql= "INSERT INTO profile (mail, idProfile, currentStatus, completeName, shownName, actualWorkPosition, phoneNumber, timezone) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pstate= conn.prepareStatement(sql);
            pstate.setString(1,obj.getEmail());
            pstate.setString(2,nameProfile);
            pstate.setString(3,currentStatus);
            pstate.setString(4,completeName);
            pstate.setString(5,shownName);
            pstate.setString(6,actualWorkPosition);
            pstate.setString(7,phoneNumber);
            pstate.setString(8,timezone);
            res=pstate.executeQuery();
            System.out.println("Profile successfully registred !");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Profile(this,obj);
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
        return new Profile(this, obj.getUser());
    }

    @Override
    public Profile select(String key) {
        return null;

    }
}
