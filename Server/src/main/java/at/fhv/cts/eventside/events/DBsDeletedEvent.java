package at.fhv.cts.eventside.events;

import java.time.LocalDateTime;

public class DBsDeletedEvent extends Event {

    public DBsDeletedEvent() {
        super(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "DBsDeletedEvent{}";
    }
}
