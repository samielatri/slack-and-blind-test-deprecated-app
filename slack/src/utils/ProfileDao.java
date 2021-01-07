package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Profile;
import model.User;

public class ProfileDao {

    public static int createProfile(User user) {

        int id = 0;
        try {
            // get the connection for the database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write the insert query
            String insertQuery = "insert into profile values(NULL,?,?,?,?,?,?,?);";

            // set PreparedStatement
            PreparedStatement statement = connection.prepareStatement(insertQuery);

            // set parameters with PreparedStatement

            statement.setString(1, user.getProfile().getCurrentStatus());
            statement.setString(2, user.getProfile().getCompleteName());
            statement.setString(3, user.getProfile().getShownName());
            statement.setString(4, user.getProfile().getActualWorkPosition());
            statement.setString(5, user.getProfile().getPhoneNumber());
            statement.setString(6, user.getProfile().getTimezone());
            statement.setInt(7, user.getId());

            // execute the statement
            statement.executeUpdate();

            PreparedStatement statement1 = connection.prepareStatement("SELECT MAX( id ) FROM profile;");

            ResultSet set = statement1.executeQuery();

            if (set.next()) {
                id = set.getInt(1);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return id;

    }

    public static User getUserProfileById(int id) {
        User user = new User();
        try {
            // get the connection for the database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write the insert query
            String sql = "select * from profile where id=" + id;

            // set PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // execute the statement
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                Profile profile = new Profile();
                profile = new Profile();
                profile.setId(set.getInt("id"));
                profile.setCurrentStatus(set.getString("currentStatus"));
                profile.setCompleteName(set.getString("completeName"));
                profile.setShownName(set.getString("shownName"));
                profile.setActualWorkPosition(set.getString("actualWorkPosition"));
                profile.setPhoneNumber(set.getString("phoneNumber"));
                profile.setTimezone(set.getString("timezone"));
                user.setId(set.getInt("userid"));
                user.setProfile(profile);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return user;

    }

    public static Profile getProfileById(int id) {
        Profile profile = null;
        try {
            // get the connection for the database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write the insert query
            String sql = "select * from profile where userid =" + id;

            // set PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // execute the statement
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                profile = new Profile();
                profile.setId(set.getInt("id"));
                profile.setCurrentStatus(set.getString("currentStatus"));
                profile.setCompleteName(set.getString("completeName"));
                profile.setShownName(set.getString("shownName"));
                profile.setActualWorkPosition(set.getString("actualWorkPosition"));
                profile.setPhoneNumber(set.getString("phoneNumber"));
                profile.setTimezone(set.getString("timezone"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return profile;

    }
}
