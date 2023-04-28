package at.fhv.cts.writeside.repositories;

import at.fhv.cts.writeside.domainModels.Room;

import java.util.List;
import java.util.Map;

public interface IRoomWriteRepository {
    Map<Integer, Room> initializeRooms();

    Room getRoomByNo(int roomNo);

    List<Room> getAllRooms();
}
