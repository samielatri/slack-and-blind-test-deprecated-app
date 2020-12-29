package Model;



import java.util.Date;

public class Message  {

    private String id;
    private User sender;
    private Date createdAt;
    private Date updatedAt;
    private String content;
    private WorkspaceChannel workspaceChannel;

//    /* constructor */
//    public Message(Profile sender, String content, Object Canal) {
//        this.id = Long.toString(ID_GENERATOR.incrementAndGet());
//        this.senderMessage = sender.getUsername();
//        this.createdAt = new Date();
//        this.updatedAt = new Date();
//        this.content = content;
//        //TODO : Mettre le workspace channel - Canal
//    }


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

    public WorkspaceChannel getWorkspaceChannel() {
        return workspaceChannel;
    }

    public void setWorkspaceChannel(WorkspaceChannel workspaceChannel) {
        this.workspaceChannel = workspaceChannel;
    }
}