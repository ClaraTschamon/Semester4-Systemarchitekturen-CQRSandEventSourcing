package at.fhv.cts.readside.repositories;

import share.domainModels.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomsReadRepository {
    Room getRoomByNumber(int roomNo);
    List<Room> getFreeRooms(LocalDate startDate, LocalDate endDate, int maxPersons);
    void putRoom(Room room);
    Room bookRoom(int roomNr, LocalDate fromDate, LocalDate toDate);
    void deleteRooms();
}
