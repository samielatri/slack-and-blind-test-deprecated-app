package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Profile;
import model.User;

/**
 *
 */
public class ProfileDao {
    /**
     *
     * @param user
     * @return
     */
    public static int createProfile(User user) {

        int id = 0;
        try {
            // get the connection for the database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write the insert query
            String insertQuery = "insert into user values(NULL,?,?,?,?,?,?,?);";

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

            PreparedStatement statement1 = connection.prepareStatement("SELECT MAX( id ) FROM user;");

            ResultSet set = statement1.executeQuery();

            if (set.next()) {
                id = set.getInt(1);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return id;

    }

    /**
     *
     * @param id
     * @return
     */
    public static User getUserProfileById(int id) {
        User user = new User();
        try {
            // get the connection for the database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write the insert query
            String sql = "select * from user where id=" + id;

            // set PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // execute the statement
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                Profile user = new Profile();
                user = new Profile();
                user.setId(set.getInt("id"));
                user.setCurrentStatus(set.getString("currentStatus"));
                user.setCompleteName(set.getString("completeName"));
                user.setShownName(set.getString("shownName"));
                user.setActualWorkPosition(set.getString("actualWorkPosition"));
                user.setPhoneNumber(set.getString("phoneNumber"));
                user.setTimezone(set.getString("timezone"));
                user.setId(set.getInt("userid"));
                user.setProfile(user);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return user;

    }

    /**
     *
     * @param id
     * @return
     */
    public static Profile getProfileById(int id) {
        Profile user = null;
        try {
            // get the connection for the database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write the insert query
            String sql = "select * from user where userid =" + id;

            // set PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // execute the statement
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                user = new Profile();
                user.setId(set.getInt("id"));
                user.setCurrentStatus(set.getString("currentStatus"));
                user.setCompleteName(set.getString("completeName"));
                user.setShownName(set.getString("shownName"));
                user.setActualWorkPosition(set.getString("actualWorkPosition"));
                user.setPhoneNumber(set.getString("phoneNumber"));
                user.setTimezone(set.getString("timezone"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return user;

    }
}
