package at.fhv.cts.readside.repositories;

import at.fhv.cts.readside.domainModels.Room;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RoomsReadRepositoryImpl implements IRoomsReadRepository {

    private Map<Integer, Room> rooms = new HashMap<>();

    @Override
    public Room getRoomByNumber(int roomNo) {
        return rooms.get(roomNo);
    }

    @Override
    public void putRoom(Room room) {
        rooms.put(room.getRoomNo(), room);
    }

    @Override
    public void deleteRooms() {
        rooms.clear();
    }

    @Override
    public Map<Integer, Room> getAllRooms() {
        return rooms;
    }
}
