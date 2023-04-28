package at.fhv.cts.writeside;

import at.fhv.cts.writeside.domainModels.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IRepositoryFacade {
    //customers
    Map<UUID, Customer> initializeCustomers();

    void createCustomer(Customer customer);

    Customer getCustomerById(String id);

    Customer getRandomCustomer(); //nur um bookings zu initialisieren in bookingWriteRepository

    void deleteCustomer(String customerId);
    //

    //rooms
    Map<Integer, Room> initializeRooms();

    Room getRoomByNo(int roomNo);

    List<Room> getAllRooms();

    //

    //bookings
    void createBooking(Booking booking);

    Booking getBookingById(String id);

    void cancelBooking(String bookingId);

    Map<String, Booking> initializeBookingList();

    Map<String, Booking> getAllBookings();
    //
}
