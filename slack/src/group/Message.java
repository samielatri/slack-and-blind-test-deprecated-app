package group;

import userManagment.*;

import java.util.Date;

public class Message{
    private Date createdAt;
    private Date updatedAt;
    private String content;
    private String idMessage;
    private String senderMessage;
    private WorkspaceChannel workspaceChannel;
    public Message(User sender, String content, Object Canal){
        this.content = content;

        this.senderMessage = senderMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setIdMessage(String idMessage) {
        this.idMessage = idMessage;
    }

    public String getIdMessage() {
        return idMessage;
    }
}