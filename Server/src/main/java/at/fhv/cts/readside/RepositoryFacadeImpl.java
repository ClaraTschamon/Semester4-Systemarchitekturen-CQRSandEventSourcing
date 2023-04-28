package at.fhv.cts.readside;

import at.fhv.cts.readside.repositories.IBookedRoomsReadRepository;
import at.fhv.cts.readside.repositories.IBookingReadRepository;
import at.fhv.cts.readside.repositories.ICustomerReadRepository;
import at.fhv.cts.readside.repositories.IRoomsReadRepository;
import at.fhv.cts.readside.domainModels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class RepositoryFacadeImpl implements IRepositoryFacade {

    @Autowired
    ICustomerReadRepository customerReadRepository;

    @Autowired
    IBookingReadRepository bookingReadRepository;

    @Autowired
    IRoomsReadRepository roomsReadRepository;

    @Autowired
    IBookedRoomsReadRepository bookedRoomsReadRepository;

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
    public void cancelBooking(Booking booking) {
        bookingReadRepository.cancelBooking(booking.getBookingId());
        bookedRoomsReadRepository.deleteBookedRooms(booking.getFromDate(), booking.getToDate(), booking.getRooms());
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
    public void putRoom(Room room) {
        roomsReadRepository.putRoom(room);
    }

    @Override
    public void deleteRooms() {
        roomsReadRepository.deleteRooms();
    }
    //

    //bookedRooms
    @Override
    public void deleteBookedRooms(LocalDate fromDate, LocalDate toDate, Set<Integer> roomNumbers) {
        bookedRoomsReadRepository.deleteBookedRooms(fromDate, toDate, roomNumbers);
    }

    @Override
    public void bookRooms(LocalDate fromDate, LocalDate toDate, Set<Integer> roomNumbers) {
        bookedRoomsReadRepository.bookRooms(fromDate, toDate, roomNumbers);
    }

    @Override
    public List<Room> getFreeRooms(LocalDate fromDate, LocalDate toDate, int numberOfPersons) {
        return bookedRoomsReadRepository.getFreeRooms(fromDate, toDate, numberOfPersons);
    }
    //
}
