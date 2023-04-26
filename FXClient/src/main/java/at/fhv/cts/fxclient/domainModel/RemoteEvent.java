package at.fhv.cts.fxclient.domainModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true) //damit ich nicht alle einzelnen attribute von jedem event deklarieren muss
public class RemoteEvent {
    private LocalDateTime timestamp;
    private String className;

    public RemoteEvent() {
        super(); //necessary for jackson databind library
    }

    public RemoteEvent(LocalDateTime timestamp, String className) {
        this.timestamp = timestamp;
        this.className = className;
    }

    //getter and setter needed for fx tableview
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
