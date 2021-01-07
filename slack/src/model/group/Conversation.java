package model.group;

import java.util.Date;

public class Conversation{
    private int id;
    private String name;
    private Date startedAt;

    /* constructors */

    public Conversation(String name,Date startedAt) {
        this.name = name;
        this.startedAt = startedAt;
    }

    /* Accessors*/
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

    public Date getStartedAt() {
        return startedAt;
    }

}