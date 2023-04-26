package at.fhv.cts.writeside.domainModel;

public class Room {

    private int roomNo;
    private int maxPersons;
    private String category;

    public Room(int rommNo, int maxPersons, String category) {
        this.roomNo = rommNo;
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
