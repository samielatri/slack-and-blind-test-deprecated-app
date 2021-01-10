package src.database;

import model.communication.Message;
import model.communication.Workspace;
import model.communication.WorkspaceChannel;
import model.user.Profile;
import model.user.User;

import java.sql.SQLException;

public class DAOFactory {

	private static final boolean isSQL = true;

	public static DAO<Workspace> workspace() throws SQLException {
		if (isSQL) {
			return new SQLWorkspaceDAO();
		}
		return new MemoryDAO<Workspace>();
	}

	public static DAO<Message> messageChannel() throws SQLException {
		if (isSQL) {
			return new SQLMessageChannelDAO();
		}
		return new MemoryDAO<Message>();
	}
	public static DAO<Message> messageDirect() throws SQLException {
		if (isSQL) {
			return new SQLMessageDirectDAO();
		}
		return new MemoryDAO<Message>();
	}

	public static DAO<WorkspaceChannel> workspaceChannel() throws SQLException {
		if (isSQL) {
			return new SQLWorkspaceChannelDAO();
		}
		return new MemoryDAO<WorkspaceChannel>();
	}
	public static DAO<Profile> profile() throws SQLException {
		if (isSQL) {
			return new SQLProfileDAO();
		}
		return new MemoryDAO<Profile>();
	}
	public static DAO<User> user() throws SQLException {
		if (isSQL) {
			return new SQLUserDAO();
		}
		return new MemoryDAO<User>();
	}

	private DAOFactory() {}
}
