package at.fhv.cts.writeside;

import at.fhv.cts.writeside.repositories.IBookingWriteRepository;
import at.fhv.cts.writeside.repositories.ICustomerWriteRepository;
import at.fhv.cts.writeside.repositories.IRoomWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import at.fhv.cts.writeside.domainModels.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class RepositoryFacadeImpl implements IRepositoryFacade {
    @Autowired
    ICustomerWriteRepository customerWriteRepository;

    @Autowired
    IRoomWriteRepository roomWriteRepository;

    @Autowired
    IBookingWriteRepository bookingWriteRepository;


    //customers
    @Override
    public Map<UUID, Customer> initializeCustomers() {
        return customerWriteRepository.initializeCustomers();
    }

    @Override
    public void createCustomer(Customer customer) {
        customerWriteRepository.createCustomer(customer);
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerWriteRepository.getCustomerById(id);
    }

    @Override
    public Customer getRandomCustomer() {
        return customerWriteRepository.getRandomCustomer(); //nur um bookings zu initialisieren in bookingWriteRepository
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerWriteRepository.deleteCustomer(customerId);
    }
    //

    //rooms
    @Override
    public Map<Integer, Room> initializeRooms() {
        return roomWriteRepository.initializeRooms();
    }

    @Override
    public Room getRoomByNo(int roomNo) {
        return roomWriteRepository.getRoomByNo(roomNo);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomWriteRepository.getAllRooms();
    }

    //

    //bookings
    @Override
    public void createBooking(Booking booking) {
        bookingWriteRepository.createBooking(booking);
    }

    @Override
    public Booking getBookingById(String id) {
        return bookingWriteRepository.getBookingById(id);
    }

    @Override
    public void cancelBooking(String bookingId) {
        bookingWriteRepository.cancelBooking(bookingId);
    }

    @Override
    public Map<String, Booking> initializeBookingList() {
        return bookingWriteRepository.initializeBookingList();
    }

    @Override
    public Map<String, Booking> getAllBookings() {
        return bookingWriteRepository.getAllBookings();
    }
    //
}
