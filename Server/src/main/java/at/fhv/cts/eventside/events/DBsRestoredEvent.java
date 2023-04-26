package at.fhv.cts.eventside.events;

import java.time.LocalDateTime;

public class DBsRestoredEvent extends Event {

    public DBsRestoredEvent(LocalDateTime timestamp) {
        super(timestamp);
    }

    @Override
    public String toString() {
        return "DBsRestoredEvent{}";
    }
}
