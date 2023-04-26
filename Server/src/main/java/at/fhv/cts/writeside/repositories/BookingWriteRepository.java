package at.fhv.cts.writeside.repositories;

import at.fhv.cts.writeside.domainModel.Booking;
import at.fhv.cts.writeside.domainModel.Customer;
import at.fhv.cts.writeside.domainModel.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class BookingWriteRepository {

    @Autowired
    private CustomerWriteRepository customerWriteRepository;

    @Autowired
    private RoomWriteRepository roomWriteRepository;

    private Map<String, Booking> bookings = new HashMap<>();

    public void createBooking(Booking booking) {
        bookings.put(booking.getId(), booking);
    }

    public Booking getBookingById(String id) {
        return bookings.get(id);
    }

    public void cancelBooking(String bookingId) {
        bookings.remove(bookingId);
    }

    public Map<String, Booking> initializeBookingList() {
        bookings = new HashMap<>();
        List<Room> allRooms = roomWriteRepository.getAllRooms(); //just for the purpose of initializing bookings
        Customer customer1 = customerWriteRepository.getRandomCustomer(); //methode nur für initialisierung der bookings
        Set<Room> rooms1 = new HashSet<>();
        rooms1.add(allRooms.get(0));
        rooms1.add(allRooms.get(1));
        Booking booking1 = new Booking("b1", LocalDate.of(2023, 5,1),
                LocalDate.of(2023, 5, 3), customer1, rooms1);


        Customer customer2 = customerWriteRepository.getRandomCustomer();
        Set<Room> rooms2 = new HashSet<>();
        rooms2.add(allRooms.get(2));
        rooms2.add(allRooms.get(3));
        Booking booking2 = new Booking("b2", LocalDate.of(2023, 4,21),
                LocalDate.of(2023, 4, 23), customer2, rooms2);

        Customer customer3 = customerWriteRepository.getRandomCustomer();
        Set<Room> rooms3 = new HashSet<>();
        rooms3.add(allRooms.get(4));
        rooms3.add(allRooms.get(5));
        rooms3.add(allRooms.get(6));
        Booking booking3 = new Booking("b3", LocalDate.of(2023, 4,25),
                LocalDate.of(2023, 4, 27), customer3, rooms3);


        Customer customer4 = customerWriteRepository.getRandomCustomer();
        Set<Room> rooms4 = new HashSet<>();
        rooms4.add(allRooms.get(7));
        Booking booking4 = new Booking("b4", LocalDate.of(2023, 4,23),
                LocalDate.of(2023, 5, 1), customer4, rooms4);

        bookings.put(booking1.getId(), booking1);
        bookings.put(booking2.getId(), booking2);
        bookings.put(booking3.getId(), booking3);
        bookings.put(booking4.getId(), booking4);

        return bookings;
    }
}
