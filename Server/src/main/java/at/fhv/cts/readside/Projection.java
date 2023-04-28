package at.fhv.cts.readside;

import share.domainModels.*;

import at.fhv.cts.readside.queries.GetBookingsQuery;
import at.fhv.cts.readside.queries.GetCustomersQuery;
import at.fhv.cts.readside.queries.GetFreeRoomsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.events.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class Projection {

    @Autowired
    IRepositoryFacade repositoryFacade;

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

        repositoryFacade.insertCustomer(customer);
    }

    public void processBookingCreatedEvent(BookingCreatedEvent event) {

        Set<Room> rooms = new HashSet<>();

        for (Integer roomNumber : event.getRooms()) {
            rooms.add(repositoryFacade.bookRoom(roomNumber, event.getFromDate(), event.getToDate()));
        }

        Customer customer = repositoryFacade.getCustomerById(event.getCustomerId());
        Booking booking = new Booking(event.getBookingId(), event.getFromDate(), event.getToDate(),
                customer, rooms);
        repositoryFacade.addBooking(booking);
    }

    public void processBookingCancelledEvent(BookingCancelledEvent event) {
        Booking booking = repositoryFacade.getBookingById(event.getBookingId());

        for (Room r : booking.getRooms()) {
            Room room = repositoryFacade.getRoomByNumber(r.getRoomNo());
            room.setReservedFrom(null);
            room.setReservedUntil(null);
            repositoryFacade.putRoom(room);
        }

        repositoryFacade.cancelBooking(event.getBookingId());
    }

    public void processDBsDeletedEvent(DBsDeletedEvent event) {
        repositoryFacade.deleteCustomer();
        repositoryFacade.deleteBookings();
        repositoryFacade.deleteRooms();
    }

    public void processRoomCreatedEvent(RoomCreatedEvent event) {

        Room room = new Room(event.getRoomNo(), event.getMaxPersons(), event.getCategory(),
                event.getReservedFrom(), event.getReservedUntil());
        repositoryFacade.putRoom(room);
    }


    //-------------------------------------------------------------------------------------------------------//

    public List<Room> handleGetFreeRoomsQuery(GetFreeRoomsQuery query) {
        return repositoryFacade.getFreeRooms(query.getFromDate(), query.getToDate(), query.getNrOfPersons());
    }

    public List<Booking> handleGetBookingsQuery(GetBookingsQuery query) {
        return repositoryFacade.getBookings(query.getFromDate(), query.getToDate());
    }

    public List<Customer> handleGetCustomerQuery(GetCustomersQuery query) {
        if (query.getName() == null) {
            return repositoryFacade.getCustomers();
        }
        return repositoryFacade.getCustomers(query.getName());
    }
}
