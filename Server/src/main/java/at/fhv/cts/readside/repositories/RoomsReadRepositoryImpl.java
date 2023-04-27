package at.fhv.cts.readside.repositories;

import share.domainModels.Room;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoomsReadRepositoryImpl implements IRoomsReadRepository {

    private Map<Integer, Room> rooms = new HashMap();

    @Override
    public Room getRoomByNumber(int roomNo) {
        return rooms.get(roomNo);
    }

    @Override
    public List<Room> getFreeRooms(LocalDate startDate, LocalDate endDate, int maxPersons) {
        List<Room> freeRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            if(room.getReservedFrom() == null && room.getReservedUntil() == null) {
                freeRooms.add(room);
            } else
            if (room.getMaxPersons() >= maxPersons
                    && (room.getReservedFrom().isEqual(startDate) || room.getReservedFrom().isBefore(startDate))
                    && (room.getReservedUntil().isEqual(endDate) || room.getReservedUntil().isBefore(endDate))
                    && !(room.getReservedUntil().isAfter(startDate))
            ) {
                freeRooms.add(room);
            }
        }
        return freeRooms;
    }

    @Override
    public void putRoom(Room room) {
        rooms.put(room.getRoomNo(), room);
    }

    @Override
    public Room bookRoom(int roomNr, LocalDate fromDate, LocalDate toDate) {
        Room room = rooms.get(roomNr);
        room.setReservedFrom(fromDate);
        room.setReservedUntil(toDate);
        rooms.put(roomNr, room);
        return room;
    }

    @Override
    public void deleteRooms() {
        rooms.clear();
    }
}
