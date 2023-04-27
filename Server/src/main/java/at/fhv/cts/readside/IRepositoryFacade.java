package at.fhv.cts.readside;

import share.domainModels.Booking;
import share.domainModels.Customer;
import share.domainModels.Room;

import java.time.LocalDate;
import java.util.List;
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
    void cancelBooking(String bookingId);
    void addBooking(Booking booking);
    void deleteBookings();
    //

    //rooms
    Room getRoomByNumber(int roomNo);
    List<Room> getFreeRooms(LocalDate startDate, LocalDate endDate, int maxPersons);
    void putRoom(Room room);
    Room bookRoom(int roomNr, LocalDate fromDate, LocalDate toDate);
    void deleteRooms();
    //
}
