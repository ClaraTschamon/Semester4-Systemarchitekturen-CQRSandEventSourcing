package at.fhv.cts.readside;

import share.domainModels.*;

import at.fhv.cts.eventside.events.*;
import at.fhv.cts.readside.queries.GetBookingsQuery;
import at.fhv.cts.readside.queries.GetCustomersQuery;
import at.fhv.cts.readside.queries.GetFreeRoomsQuery;
import at.fhv.cts.readside.repositories.BookingReadRepository;
import at.fhv.cts.readside.repositories.CustomerReadRepository;
import at.fhv.cts.readside.repositories.RoomsReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class Projection {

    @Autowired
    private CustomerReadRepository customerReadRepository;

    @Autowired
    private BookingReadRepository bookingReadRepository;

    @Autowired
    private RoomsReadRepository freeRoomsReadRepository;

    public void processEvent(Event event) {
        if(event instanceof BookingCancelledEvent) {
            processBookingCancelledEvent((BookingCancelledEvent) event);
        } else if (event instanceof BookingCreatedEvent) {
            processBookingCreatedEvent((BookingCreatedEvent) event);
        } else if(event instanceof CustomerCreatedEvent) {
            processCustomerCreatedEvent((CustomerCreatedEvent) event);
        } else if(event instanceof DBsDeletedEvent) {
            processDBsDeletedEvent((DBsDeletedEvent) event);
        } else if(event instanceof RoomCreatedEvent) {
            processRoomCreatedEvent((RoomCreatedEvent) event);
        }
    }

    public void processCustomerCreatedEvent(CustomerCreatedEvent event) {
        Customer customer = new Customer(event.getCustomerId(), event.getName(), event.getAddress(),
                event.getDateOfBirth());

        customerReadRepository.insertCustomer(customer);
    }

    public void processBookingCreatedEvent(BookingCreatedEvent event) {

        Set<Room> rooms = new HashSet<>();

        for (Integer roomNumber : event.getRooms()) {
            rooms.add(freeRoomsReadRepository.bookRoom(roomNumber, event.getFromDate(), event.getToDate()));
        }

        Customer customer = customerReadRepository.getCustomerById(event.getCustomerId());
        Booking booking = new Booking(event.getBookingId(), event.getFromDate(), event.getToDate(),
                customer, rooms);
        bookingReadRepository.addBooking(booking);
    }

    public void processBookingCancelledEvent(BookingCancelledEvent event) {
        Booking booking = bookingReadRepository.getBookingById(event.getBookingId());

        for (Room r : booking.getRooms()) {
            Room room = freeRoomsReadRepository.getRoomByNumber(r.getRoomNo());
            room.setReservedFrom(LocalDate.MIN);
            room.setReservedUntil(LocalDate.MAX);
            freeRoomsReadRepository.updateRoom(room);
        }

        bookingReadRepository.cancelBooking(event.getBookingId());
    }

    public void processDBsDeletedEvent(DBsDeletedEvent event) {
        customerReadRepository.delete();
        bookingReadRepository.delete();
        freeRoomsReadRepository.delete();
    }

    public void processRoomCreatedEvent(RoomCreatedEvent event) {

        Room room = new Room(event.getRoomNo(), event.getMaxPersons(), event.getCategory(),
                LocalDate.MIN, LocalDate.MAX);
        freeRoomsReadRepository.addNewRoom(room);
    }


    //-------------------------------------------------------------------------------------------------------//

    public List<Room> handleGetFreeRoomsQuery(GetFreeRoomsQuery query) {
        return freeRoomsReadRepository.getFreeRooms(query.getFromDate(), query.getToDate(), query.getNrOfPersons());
    }

    public List<Booking> handleGetBookingsQuery(GetBookingsQuery query) {
        return bookingReadRepository.getBookings(query.getFromDate(), query.getToDate());
    }

    public List<Customer> handleGetCustomerQuery(GetCustomersQuery query) {
        if (query.getName() == null) {
            return customerReadRepository.getCustomers();
        }
        return customerReadRepository.getCustomers(query.getName());
    }
}
