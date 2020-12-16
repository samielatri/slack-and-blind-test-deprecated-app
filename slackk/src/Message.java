import java.util.Date;

public class Message{
    private Date createdAt;
    private Date updatedAt;
    private String content;
    private String statut;
    private Channel channel;
    private D groupe de discussion (privée 1 personne / n personnes)
    public Message(String content, Date createdAt, Date updatedAt){
        this.content = content;
        this.createdAt=createdAt;
        this.updatedAt = updatedAt;
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

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getStatut() {
        return statut;
    }
}