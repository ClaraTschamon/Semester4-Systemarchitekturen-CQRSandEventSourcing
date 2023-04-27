package at.fhv.cts.readside;

import at.fhv.cts.readside.repositories.IBookingReadRepository;
import at.fhv.cts.readside.repositories.ICustomerReadRepository;
import at.fhv.cts.readside.repositories.IRoomsReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import share.domainModels.Booking;
import share.domainModels.Customer;
import share.domainModels.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class RepositoryFacadeImpl implements IRepositoryFacade {

    @Autowired
    ICustomerReadRepository customerReadRepository;

    @Autowired
    IBookingReadRepository bookingReadRepository;

    @Autowired
    IRoomsReadRepository roomsReadRepository;

    //customers
    @Override
    public Customer getCustomerById(UUID id) {
        return customerReadRepository.getCustomerById(id);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerReadRepository.getCustomers();
    }

    @Override
    public List<Customer> getCustomers(String name) {
        return customerReadRepository.getCustomers(name);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerReadRepository.insertCustomer(customer);
    }

    @Override
    public void deleteCustomer() {
        customerReadRepository.deleteCustomer();
    }
    //

    //bookings
    @Override
    public Booking getBookingById(String bookingId) {
        return bookingReadRepository.getBookingById(bookingId);
    }

    @Override
    public List<Booking> getBookings(LocalDate arrivalDate, LocalDate departureDate) {
        return bookingReadRepository.getBookings(arrivalDate, departureDate);
    }

    @Override
    public void cancelBooking(String bookingId) {
        bookingReadRepository.cancelBooking(bookingId);
    }

    @Override
    public void addBooking(Booking booking) {
        bookingReadRepository.addBooking(booking);
    }

    @Override
    public void deleteBookings() {
        bookingReadRepository.deleteBookings();
    }
    //

    //rooms
    @Override
    public Room getRoomByNumber(int roomNo) {
        return roomsReadRepository.getRoomByNumber(roomNo);
    }

    @Override
    public List<Room> getFreeRooms(LocalDate startDate, LocalDate endDate, int maxPersons) {
        return roomsReadRepository.getFreeRooms(startDate, endDate, maxPersons);
    }

    @Override
    public void putRoom(Room room) {
        roomsReadRepository.putRoom(room);
    }

    @Override
    public Room bookRoom(int roomNr, LocalDate fromDate, LocalDate toDate) {
        return roomsReadRepository.bookRoom(roomNr, fromDate, toDate);
    }

    @Override
    public void deleteRooms() {
        roomsReadRepository.deleteRooms();
    }
    //
}
