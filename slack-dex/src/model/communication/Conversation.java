package src.model.communication;

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

    @Override
    public String getId() {
        return id;
    }

    /* Accessors*/

}