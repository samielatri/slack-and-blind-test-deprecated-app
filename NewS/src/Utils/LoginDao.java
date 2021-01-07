package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Model.User;
import Model.Workspace;
import javafx.scene.image.Image;

public class LoginDao {

	public static boolean register(User user) {

		boolean flag = false;
		try {
			// get the connection for the database
			Connection connection = DBConnection.getConnectionToDatabase();

			// write the insert query
			String insertQuery = "insert into user values(NULL,?,?,?,?,?,?,?,?)";

			// set PreparedStatement
			PreparedStatement statement = connection.prepareStatement(insertQuery);

			// set parameters with PreparedStatement
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getCurrentStatus());
			statement.setString(4, user.getCompleteName());
			statement.setString(5, user.getShownName());
			statement.setString(6, user.getActualWorkPosition());
			statement.setString(7, user.getPhoneNumber());
			statement.setString(8, user.getTimezone());

			// execute the statement
			statement.executeUpdate();

			flag = true;

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return flag;

	}

	public static User login(String email, String password) {
		User user = null;
		try {
			// get the connection for the database
			Connection connection = DBConnection.getConnectionToDatabase();

			// write the insert query
			String sql = "select * from user where email=? and password=?";

			// set PreparedStatement
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);

			// execute the statement
			ResultSet set = statement.executeQuery();

			if (set.next()) {
				user = new User();
				user.setId(set.getInt("id"));
				user.setEmail(set.getString("email"));
				user.setPassword(set.getString("password"));
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
