package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.User;
import Model.Workspace;

public class WorkplaceDao {

	public static boolean createWorkspace(Workspace workspace) {
		boolean flag = false;
		try {
			// get the connection for the database
			Connection connection = DBConnection.getConnectionToDatabase();

			// write the insert query
			String insertQuery = "insert into workspace values (NULL,?,?,?,?,?) ";

			// set PreparedStatement
			PreparedStatement statement = connection.prepareStatement(insertQuery);

			// set parameters with PreparedStatement
			statement.setString(1, workspace.getName());
			statement.setInt(2, workspace.getCreatedBy());

			statement.setString(3, fomateData(workspace.getAdmins()));
			statement.setString(4, fomateData(workspace.getMembers()));
			statement.setString(5, fomateData(workspace.getBannedUsers()));

			// execute the statement
			statement.executeUpdate();

			flag = true;

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return flag;
	}

	public static String fomateData(ArrayList<User> list) {

		String data = "";
		for (User user : list) {
			data += "," + user.getId();
		}
		return data;
	}

	public static User getUserById(int id) {
		User user = null;
		try {
			// get the connection for the database
			Connection connection = DBConnection.getConnectionToDatabase();

			// write the insert query
			String sql = "select * from user where id = " + id;

			// set PreparedStatement
			PreparedStatement statement = connection.prepareStatement(sql);

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

	public static ArrayList<Workspace> getWorkspace() {

		ArrayList<Workspace> list = new ArrayList<Workspace>();
		ArrayList<User> members = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		ArrayList<User> bannedUsers = new ArrayList<>();

		try {
			// get the connection for the database
			Connection connection = DBConnection.getConnectionToDatabase();

			// write the insert query
			String sql = "select * from workspace";

			// set PreparedStatement
			PreparedStatement statement = connection.prepareStatement(sql);

			// execute the statement
			ResultSet set = statement.executeQuery();

			if (set.next()) {
				Workspace workspace = new Workspace();
				workspace.setId(set.getInt("id"));
				workspace.setName(set.getString("name"));
				workspace.setCreatedBy(set.getInt("createdby"));
				String adminString = set.getString("admin");
				String memberString = set.getString("member");
				String bannedUsersString = set.getString("bannedusers");

				System.out.println("adminString/" + adminString);
				System.out.println("memberString/" + memberString);
				System.out.println("bannedUsersString/" + bannedUsersString);

				members = parseData(memberString);
				admins = parseData(adminString);
				bannedUsers = parseData(bannedUsersString);
				
				workspace.setAdmins(admins);
				workspace.setBannedUsers(bannedUsers);
				workspace.setMembers(members);

				list.add(workspace);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return list;
	}

	public static User checkUserAsWorkspaceMember(ArrayList<Workspace> list, int id) {
		
//		for (Workspace workspace : list) {
//			if(workspace.get) {
//				
//			}
//		}
		
		return null;
		
	}

	public static ArrayList<Workspace> checkUserAsWorkspaceAdmin(int id) {
		ArrayList<Workspace> list = new ArrayList<Workspace>();
		try {
			// get the connection for the database
			Connection connection = DBConnection.getConnectionToDatabase();

			// write the insert query
			String sql = "select * from workspace where email=? and password=?";

			// set PreparedStatement
			PreparedStatement statement = connection.prepareStatement(sql);

			// execute the statement
			ResultSet set = statement.executeQuery();

			if (set.next()) {
				Workspace workspace = new Workspace();
				workspace.setId(set.getInt("id"));
				workspace.setName(set.getString("name"));

			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return list;
	}

	public static ArrayList<User> parseData(String data) {
		ArrayList<User> list = new ArrayList<>();
		String[] split = data.split(",");
		if (split.length > 0) {
			for (String string : split) {
				System.out.println("split: " + string);
				if (!string.equals("")) {
					int id = Integer.parseInt(string);
					list.add(getUserById(id));
				}
			}
		}
		return list;
	}

}
