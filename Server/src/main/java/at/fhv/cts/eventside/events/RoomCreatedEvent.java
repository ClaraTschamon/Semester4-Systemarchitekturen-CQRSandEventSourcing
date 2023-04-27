package at.fhv.cts.eventside.events;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RoomCreatedEvent extends Event {
    private int roomNo;
    private int maxPersons;
    private String category;
    private LocalDate reservedFrom;
    private LocalDate reservedUntil;

    public RoomCreatedEvent(int roomNo, int maxPersons, String category, LocalDateTime timestamp,
                            LocalDate reservedFrom, LocalDate reservedUntil) {
        super(timestamp);
        this.roomNo = roomNo;
        this.maxPersons = maxPersons;
        this.category = category;
        this.reservedFrom = reservedFrom;
        this.reservedUntil = reservedUntil;
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

    public LocalDate getReservedFrom() {
        return reservedFrom;
    }

    public void setReservedFrom(LocalDate reservedFrom) {
        this.reservedFrom = reservedFrom;
    }

    public LocalDate getReservedUntil() {
        return reservedUntil;
    }

    public void setReservedUntil(LocalDate reservedUntil) {
        this.reservedUntil = reservedUntil;
    }

    @Override
    public String toString() {
        return "RoomCreatedEvent{" +
                "roomNo=" + roomNo +
                ", maxPersons=" + maxPersons +
                ", category='" + category + '\'' +
                ", reservedFrom=" + reservedFrom +
                ", reservedUntil=" + reservedUntil +
                '}';
    }
}
