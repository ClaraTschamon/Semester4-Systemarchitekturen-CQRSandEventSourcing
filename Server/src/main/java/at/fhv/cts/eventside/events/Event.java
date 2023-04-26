package at.fhv.cts.eventside.events;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property="className")
public abstract class Event {

    private LocalDateTime timestamp;

    protected Event(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Event{" +
                "timestamp=" + timestamp +
                '}';
    }
}
