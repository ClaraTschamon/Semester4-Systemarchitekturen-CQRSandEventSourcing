package share.events;

import java.time.LocalDateTime;

public class DBsRestoredEvent extends Event {

    public DBsRestoredEvent() {
        super(LocalDateTime.now());
    }

    public DBsRestoredEvent(LocalDateTime timestamp) {
        super(timestamp);
    }

    @Override
    public String toString() {
        return "DBsRestoredEvent{}";
    }
}
