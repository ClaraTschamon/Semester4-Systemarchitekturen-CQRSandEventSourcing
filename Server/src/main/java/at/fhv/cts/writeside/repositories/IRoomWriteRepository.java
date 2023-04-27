package at.fhv.cts.writeside.repositories;

import share.domainModels.Room;

import java.util.List;
import java.util.Map;

public interface IRoomWriteRepository {
    Map<Integer, Room> initializeRooms();

    Room getRoomByNo(int roomNo);

    List<Room> getAllRooms();

    void freeRoom(int roomNo);
}
