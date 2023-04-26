package at.fhv.cts.readside.domainModel;

import java.time.LocalDate;

public class Room {
    private int roomNo;
    private int maxPersons;
    private String category;
    private LocalDate reservedFrom;
    private LocalDate reservedUntil;

    public Room(int roomNo, int maxPersons, String category, LocalDate reservedFrom, LocalDate reservedUntil) {
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
}
