package at.fhv.cts.fxclient.domainModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteRoom {
    private int roomNo;
    private int maxPersons;
    private String category;

    public RemoteRoom() { //necessary for jackson databind library
        super();
    } //necessary for jackson databind library

    public RemoteRoom (int rommNo, int maxPersons, String category) {
        this.roomNo = rommNo;
        this.maxPersons = maxPersons;
        this.category = category;
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

    @Override
    public String toString() { //for tableview
        return roomNo + " ";
    }
}
