package at.fhv.cts.readside;

import at.fhv.cts.readside.domainModels.Booking;
import at.fhv.cts.readside.domainModels.Customer;
import at.fhv.cts.readside.domainModels.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IRepositoryFacade {

    //customers
    Customer getCustomerById(UUID id);
    List<Customer> getCustomers();
    List<Customer> getCustomers(String name);
    void insertCustomer(Customer customer);
    void deleteCustomer();
    //

    //bookings
    Booking getBookingById(String bookingId);
    List<Booking> getBookings(LocalDate arrivalDate, LocalDate departureDate);
    void cancelBooking(Booking booking);
    void addBooking(Booking booking);
    void deleteBookings();
    //

    //rooms
    Room getRoomByNumber(int roomNo);
    void putRoom(Room room);
    void deleteRooms();
    //

    //bookedRooms
    //TODO: why never used?
    void deleteBookedRooms(LocalDate fromDate, LocalDate toDate, Set<Integer> roomNumbers);
    void bookRooms(LocalDate fromDate, LocalDate toDate, Set<Integer> roomNumbers);
    List<Room> getFreeRooms(LocalDate fromDate, LocalDate toDate, int numberOfPersons);
    //
}
