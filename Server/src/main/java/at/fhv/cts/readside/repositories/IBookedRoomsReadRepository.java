package at.fhv.cts.readside.repositories;

import at.fhv.cts.readside.domainModels.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IBookedRoomsReadRepository {
    void deleteBookedRooms(LocalDate fromDate, LocalDate toDate, Set<Integer> roomNumbers);
    void bookRooms(LocalDate fromDate, LocalDate toDate, Set<Integer> roomNumbers);
    List<Room> getFreeRooms(LocalDate fromDate, LocalDate toDate, int numberOfPersons);
    void deleteAllBookedRooms();
}
