package Model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private User sender;
	private Date createdAt;
	private Date updatedAt;
	private String content;
	private Workspace workspace;

//    /* constructor */
	public Message(User sender, String content, Workspace workspace) {
		this.sender = sender;
		this.workspace = workspace;
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", sender=" + sender + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", content=" + content + ", workspace=" + workspace + "]";
	}

}