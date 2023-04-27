package at.fhv.cts.writeside.repositories;

import share.domainModels.Room;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RoomWriteRepository implements IRoomWriteRepository {

    private Map<Integer, Room> rooms;

    @Override
    public Map<Integer, Room> initializeRooms() {
        rooms = new HashMap();
        List<String> categories = Arrays.asList("Single", "Double", "Family", "Suite");
        int roomNo = 1;
        for (int i = 0; i < 20; i++) {
            int randomMaxPersonNr = (int) ((Math.random() * 4) + 1); //generates random number between 1 and 4
            int randomCategory = (int) ((Math.random() * 4)); //generates random number between 0 and 3
            rooms.put(roomNo, new Room(roomNo, randomMaxPersonNr, categories.get(randomCategory),
                    null, null));
            roomNo++;
        }

        return rooms;
    }

    @Override
    public Room getRoomByNo(int roomNo) {
        return rooms.get(roomNo);
    }

    @Override
    public List<Room> getAllRooms() { //just for the purpose of initializing bookings in bookingWriteRepository
        return new ArrayList<>(rooms.values());
    }

    @Override
    public void freeRoom(int roomNo) {
        Room room = rooms.get(roomNo);
        room.setReservedFrom(null);
        room.setReservedUntil(null);
    }
}
