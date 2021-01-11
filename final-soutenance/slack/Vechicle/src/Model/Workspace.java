package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Workspace implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* attributes */
	private int id;
	private String name; // name of the workspace
	private ArrayList<WorkspaceChannel> workspaceChannels; // list of the channels of the workspace
	private ArrayList<User> members = new ArrayList<>(); // list of the users of the workspace
	private ArrayList<User> admins = new ArrayList<>(); // list of the administrators of the workspace
	private ArrayList<User> bannedUsers = new ArrayList<>(); // list of the banned users of the workspace
	private ArrayList<Message> conversation = new ArrayList<>();
	private int createdBy;
	private String channel;

	/* constructors */
	public Workspace(String name) {
		this.name = name;
	}

	public Workspace() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<WorkspaceChannel> getWorkspaceChannels() {
		return workspaceChannels;
	}

	public void setWorkspaceChannels(ArrayList<WorkspaceChannel> workspaceChannels) {
		this.workspaceChannels = workspaceChannels;
	}

	public ArrayList<User> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<User> members) {
		this.members = members;
	}

	public ArrayList<User> getAdmins() {
		return admins;
	}

	public void setAdmins(ArrayList<User> admins) {
		this.admins = admins;
	}

	public ArrayList<User> getBannedUsers() {
		return bannedUsers;
	}

	public void setBannedUsers(ArrayList<User> bannedUsers) {
		this.bannedUsers = bannedUsers;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public ArrayList<Message> getConversation() {
		return conversation;
	}

	public void setConversation(ArrayList<Message> conversation) {
		this.conversation = conversation;
	}

	@Override
	public String toString() {
		return "Workspace [id=" + id + ", name=" + name + ", members="
				+ members + ", admins=" + admins + ", bannedUsers=" + bannedUsers + ", createdBy=" + createdBy
				+ ", channel=" + channel + "]";
	}

}
