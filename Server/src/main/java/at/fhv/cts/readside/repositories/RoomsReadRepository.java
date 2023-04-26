package at.fhv.cts.readside.repositories;

import share.domainModels.Room;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class RoomsReadRepository {

    private Map<Integer, Room> rooms = new HashMap();

    public Room getRoomByNumber(int roomNo) {
        return rooms.get(roomNo);
    }

    public List<Room> getFreeRooms(LocalDate startDate, LocalDate endDate, int maxPersons) {
        return rooms.values().stream()
                .filter(room -> room.getMaxPersons() >= maxPersons) // filter out rooms with insufficient capacity
                //.filter(room -> room.getReservedFrom().isEqual(startDate) || room.getReservedFrom().isBefore(startDate)
                //&& (room.getReservedUntil().isEqual(endDate) || room.getReservedUntil().isAfter(endDate))) // filter out rooms that are already booked
                .filter(room -> ((room.getReservedFrom().isBefore(startDate) || room.getReservedFrom().isEqual(startDate)) ||
                                room.getReservedUntil().isAfter(endDate))
                        )
                .collect(Collectors.toList());
    }

    public void updateRoom(Room room) {
        rooms.put(room.getRoomNo(), room);
    }

    public Room bookRoom(int roomNr, LocalDate fromDate, LocalDate toDate) {
        Room room = rooms.get(roomNr);
        room.setReservedFrom(fromDate);
        room.setReservedUntil(toDate);
        rooms.put(roomNr, room);
        return room;
    }

    public void delete() {
        rooms.clear();
    }

    public void addNewRoom(Room room) {
        rooms.put(room.getRoomNo(), room);
    }
}
