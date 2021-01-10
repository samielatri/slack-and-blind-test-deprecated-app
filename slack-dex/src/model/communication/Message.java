package model.communication;

import model.HasId;
import model.user.Profile;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Message implements HasId {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private String id;
    private String idSenderMessage;
    private Date createdAt;
    private Date updatedAt;
    private String content;
    private String idCh;
    private int idConversation;

    /* constructor */
    public Message(Profile sender, String content){
        this.id = Long.toString(ID_GENERATOR.incrementAndGet());
        this.idSenderMessage = sender.getId();
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

    public String getIdSenderMessage() {
        return idSenderMessage;
    }

    public void setIdSenderMessage(String senderMessage) {
        this.idSenderMessage = senderMessage;
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

    public String getIdCh() {
        return idCh;
    }

    public void setIdCh(String idCh) {
        this.idCh = idCh;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
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