package at.fhv.cts.readside.repositories;

import at.fhv.cts.readside.domainModels.Room;

import java.util.Map;

public interface IRoomsReadRepository {
    Room getRoomByNumber(int roomNo);

    void putRoom(Room room);

    void deleteRooms();

    Map<Integer, Room> getAllRooms();
}
