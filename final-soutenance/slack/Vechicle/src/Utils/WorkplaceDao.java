package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import Model.Profile;
import Model.User;
import Model.Workspace;
import Model.WorkspaceChannel;

public class WorkplaceDao {

	public static boolean createWorkspace(Workspace workspace) {

		String chanel = "" + ((int) (Math.random() * 9000) + 1000);

		boolean flag = false;
		try {
			// get the connection for the database
			Connection connection = DBConnection.getConnectionToDatabase();

			// write the insert query
			String insertQuery = "insert into workspace values (NULL,?,?,?,?,?,?) ";

			// set PreparedStatement
			PreparedStatement statement = connection.prepareStatement(insertQuery);

			// set parameters with PreparedStatement
			statement.setString(1, workspace.getName());
			statement.setInt(2, workspace.getCreatedBy());
			statement.setString(3, fomateData(workspace.getAdmins()));
			statement.setString(4, fomateData(workspace.getMembers()));
			statement.setString(5, fomateData(workspace.getBannedUsers()));
			statement.setString(6, chanel);
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
			data += "," + user.getProfile().getId();
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

			while (set.next()) {
				Workspace workspace = new Workspace();
				workspace.setId(set.getInt("id"));
				workspace.setName(set.getString("name"));
				workspace.setCreatedBy(set.getInt("createdby"));
				workspace.setChannel(set.getString("channel"));

				String adminString = set.getString("admin");
				String memberString = set.getString("member");
				String bannedUsersString = set.getString("bannedusers");

//				System.out.println("adminString/" + adminString);
//				System.out.println("memberString/" + memberString);
//				System.out.println("bannedUsersString/" + bannedUsersString);

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

	public static ArrayList<Workspace> getUserWorkspace(ArrayList<Workspace> list, int id) {

		ArrayList<Workspace> userWorkspaces = new ArrayList<>();

		for (Workspace workspace : list) {
			ArrayList<User> admins = workspace.getAdmins();
			ArrayList<User> members = workspace.getMembers();

			for (User admin : admins) {
				if (admin.getId() == id) {
					userWorkspaces.add(workspace);
				}
			}

			for (User member : members) {
				if (member.getId() == id) {
					userWorkspaces.add(workspace);
				}
			}

		}

		return userWorkspaces;

	}

	public static ArrayList<User> parseData(String data) {
		ArrayList<User> list = new ArrayList<>();
		String[] split = data.split(",");
		if (split.length > 0) {
			for (String string : split) {
				if (!string.equals("")) {
					int id = Integer.parseInt(string);
					User userProfile = ProfileDao.getUserProfileById(id);
					User user = getUserById(userProfile.getId());
					user.setProfile(userProfile.getProfile());
					list.add(user);
				}
			}
		}
		return list;
	}

	public static void updateWorkspace(Workspace updatedWorkspace, int userId) {

		try {

			Connection connection = DBConnection.getConnectionToDatabase();

			String insertQuery = "update workspace set member = ? where id = " + updatedWorkspace.getId();

			// set PreparedStatement
			PreparedStatement statement = connection.prepareStatement(insertQuery);

			statement.setString(1, fomateData(updatedWorkspace.getMembers()));

			// execute the statement
			statement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
}
