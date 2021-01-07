package database;

import model.group.Message;
import model.group.Workspace;
import model.group.WorkspaceChannel;
import model.user.Profile;
import model.user.User;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 18/12/2020
 */

public class DAOFactory {

	private static final boolean isSQL = false;

	public static DAO<Workspace> workspace() {
		if (isSQL) {
			return new SQLWorkspaceDAO();
		}
		return new MemoryDAO<>();
	}

	public static DAO<Message> messageChannel() {
		if (isSQL) {
			return new SQLMessageChannelDAO();
		}
		return new MemoryDAO<>();
	}
	public static DAO<Message> messageDirect() {
		if (isSQL) {
			return new SQLMessageDirectDAO();
		}
		return new MemoryDAO<>();
	}

	public static DAO<WorkspaceChannel> workspaceChannel() {
		if (isSQL) {
			return new SQLWorkspaceChannelDAO();
		}
		return new MemoryDAO<>();
	}
	public static DAO<Profile> profile() {
		if (isSQL) {
			return new SQLProfileDAO();
		}
		return new MemoryDAO<>();
	}
	public static DAO<User> user() {
		if (isSQL) {
			return new SQLUserDAO();
		}
		return new MemoryDAO<>();
	}

	private DAOFactory() {}
}
