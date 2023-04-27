package at.fhv.cts.fxclient.domainModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteRoom {
    private int roomNo;
    private int maxPersons;
    private String category;
    //private LocalDate reservedFrom; //jackson databind library needs this field to construct object. I dont't show it in view
    //private LocalDate reservedUntil;

    public RemoteRoom() { //necessary for jackson databind library
        super();
    } //necessary for jackson databind library

    public RemoteRoom (int rommNo, int maxPersons, String category /*LocalDate reservedFrom, LocalDate reservedUntil*/) {
        this.roomNo = rommNo;
        this.maxPersons = maxPersons;
        this.category = category;
        //this.reservedFrom = reservedFrom;
        //this.reservedUntil = reservedUntil;
    }

    //getter and setter needed for fx tableview
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

    /*public LocalDate getReservedFrom() {
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
    }*/

    @Override
    public String toString() { //for tableview
        return roomNo + " ";
    }
}
