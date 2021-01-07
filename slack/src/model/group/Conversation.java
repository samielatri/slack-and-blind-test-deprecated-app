package model.group;

import java.util.Date;

public class Conversation{
    private int id;
    private String name;
    private Date startedAt;

    /*constroctors*/

    public Conversation(String name,Date startedAt) {
        this.name = name;
        this.startedAt = startedAt;
    }

    /* Accessors*/
    public String getId() {
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

    public Date getStartedAt() {
        return startedAt;
    }

}