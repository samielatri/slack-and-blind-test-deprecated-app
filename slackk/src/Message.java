import java.util.Date;

public class Message{
    private Date createdAt;
    private Date updatedAt;
    private String content;
    private String idMessage;
    private String senderMessage;
    private Channel channel;
    private D groupe de discussion (privée 1 personne / n personnes)
    public Message(String content, Date createdAt, Date updatedAt, String senderMessage){
        this.content = content;
        this.createdAt=createdAt;
        this.updatedAt = updatedAt;
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