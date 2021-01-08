package model.communication;

import model.HasId;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Conversation implements HasId {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private String id;
    private String name;
    private Date startedAt;

    /* constructors */

    public Conversation(String name,Date startedAt) {
        this.id = Long.toString(ID_GENERATOR.incrementAndGet());
        this.name = name;
        this.startedAt = startedAt;
    }

    public static AtomicLong getIdGenerator() {
        return ID_GENERATOR;
    }

    public void setId(String id) {
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

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startedAt=" + startedAt +
                '}';
    }

    /* Accessors*/

}