package at.fhv.cts.eventside.events;

import java.time.LocalDateTime;

public class RoomCreatedEvent extends Event {
    private int roomNo;
    private int maxPersons;
    private String category;

    public RoomCreatedEvent(int roomNo, int maxPersons, String category, LocalDateTime timestamp) {
        super(timestamp);
        this.roomNo = roomNo;
        this.maxPersons = maxPersons;
        this.category = category;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getMaxPersons() {
        return maxPersons;
    }

    public void setMaxPersons(int maxPersons) {
        this.maxPersons = maxPersons;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "RoomCreatedEvent{" +
                "roomNo=" + roomNo +
                ", maxPersons=" + maxPersons +
                ", category='" + category + '\'' +
                '}';
    }
}
