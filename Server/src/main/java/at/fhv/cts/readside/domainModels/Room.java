package at.fhv.cts.readside.domainModels;

public class Room {
    private int roomNo;
    private int maxPersons;
    private String category;

    public Room(int roomNo, int maxPersons, String category) {
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
}
