package model.group;

import model.HasId;
import model.user.Profile;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Message implements HasId {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private String id;
    private String senderMessage;
    private Date createdAt;
    private Date updatedAt;
    private String content;
    private WorkspaceChannel workspaceChannel;

    /* constructor */
    public Message(Profile sender, String content) {
        this.id = Long.toString(ID_GENERATOR.incrementAndGet());
        this.senderMessage = sender.getUsername();
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.content = content;
    }

    /* accessors */
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
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

    public WorkspaceChannel getWorkspaceChannel() {
        return workspaceChannel;
    }

    public void setWorkspaceChannel(WorkspaceChannel workspaceChannel) {
        this.workspaceChannel = workspaceChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        //TODO : Change format with date ...
        return content;
    }
}