package Model;


import java.util.ArrayList;

public class WorkspaceChannel {

	/* attributes */
	private int id;
	private String name; // name of the workspace channel
	private ArrayList<Message> conversation; // list of messages in a workspace channel
	private ArrayList<User> members; // list of the users of the workspace
	private ArrayList<User> admins; // list of the administrators of the workspace

	/* constructors */
	public WorkspaceChannel(String name) {
		this.name = name;
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

	public ArrayList<Message> getConversation() {
		return conversation;
	}

	public void setConversation(ArrayList<Message> conversation) {
		this.conversation = conversation;
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
}
