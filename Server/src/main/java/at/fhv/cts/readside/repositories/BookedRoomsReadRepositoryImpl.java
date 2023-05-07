package at.fhv.cts.readside.repositories;

import at.fhv.cts.readside.domainModels.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class BookedRoomsReadRepositoryImpl implements IBookedRoomsReadRepository {
    @Autowired
    private IRoomsReadRepository roomsReadRepository;

    private HashMap<LocalDate, List<Integer>> bookedRooms = new HashMap<>(); //date, bookedRooms

    public void deleteBookedRooms(LocalDate fromDate, LocalDate toDate, Set<Integer> roomNumbers) {
        LocalDate startDate = fromDate;
        while (!startDate.isAfter(toDate)) {
            List<Integer> currentBookedRooms = bookedRooms.get(startDate);
            if (currentBookedRooms != null) {
                List<Integer> remainingBookedRooms = new ArrayList<>();
                for (Integer roomNo : currentBookedRooms) {
                    if (!roomNumbers.contains(roomNo)) {
                        remainingBookedRooms.add(roomNo);
                    }
                }
                bookedRooms.put(startDate, remainingBookedRooms);
            }
            startDate = startDate.plusDays(1);
        }
    }

    public void bookRooms(LocalDate fromDate, LocalDate toDate, Set<Integer> roomNumbers) {
        LocalDate startDate = fromDate;
        while(!startDate.isAfter(toDate)) {
            for(int roomNo : roomNumbers) {
                //add entry to bookedRooms if not exists
                if(!bookedRooms.containsKey(startDate)) {
                    bookedRooms.put(startDate, new ArrayList<>());
                }
                bookedRooms.get(startDate).add(roomNo);
            }
            startDate = startDate.plusDays(1);
        }
    }

    public List<Room> getFreeRooms(LocalDate fromDate, LocalDate toDate, int numberOfPersons) {
        Map<Integer, Room> allRooms = roomsReadRepository.getAllRooms();
        Map<Integer, Room> freeRooms = new HashMap<>(allRooms);

        LocalDate startDate = fromDate;
        while (!startDate.isAfter(toDate)) {
            List<Integer> bookedRoomNumbers = bookedRooms.getOrDefault(startDate, Collections.emptyList());
            for (int roomNo : bookedRoomNumbers) {
                //don't remove room if checkOut date is on fromDate
                if (!startDate.equals(fromDate)) {
                    freeRooms.remove(roomNo);
                }
            }
            startDate = startDate.plusDays(1);
        }

        // filter out all rooms which are too small
        freeRooms.entrySet().removeIf(entry -> entry.getValue().getMaxPersons() < numberOfPersons);

        return new ArrayList<>(freeRooms.values());
    }

    public void deleteAllBookedRooms() {
        bookedRooms.clear();
    }
}
