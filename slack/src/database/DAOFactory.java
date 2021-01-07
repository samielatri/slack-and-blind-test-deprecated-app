package database;

import model.group.Message;
import model.group.Workspace;
import model.group.WorkspaceChannel;

public class DAOFactory {

	private static final boolean isSQL = false;

	public static DAO<Workspace> workspace() {
		if (isSQL) {
			return new SQLWorkspaceDAO();
		}
		return new MemoryDAO<>();
	}

	public static DAO<Message> message() {
		if (isSQL) {
			return new SQLMessageDAO();
		}
		return new MemoryDAO<>();
	}

	public static DAO<WorkspaceChannel> workspaceChannel() {
		if (isSQL) {
			return new SQLWorkspaceChannelDAO();
		}
		return new MemoryDAO<>();
	}

	private DAOFactory() {}
}
