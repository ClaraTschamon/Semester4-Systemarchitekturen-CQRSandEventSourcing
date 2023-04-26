package at.fhv.cts.readside;

import at.fhv.cts.eventside.events.*;
import at.fhv.cts.readside.domainModel.Booking;
import at.fhv.cts.readside.domainModel.Customer;
import at.fhv.cts.readside.domainModel.Room;
import at.fhv.cts.readside.queries.GetBookingsQuery;
import at.fhv.cts.readside.queries.GetCustomersQuery;
import at.fhv.cts.readside.queries.GetFreeRoomsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class ReadsideRestController {

    @Autowired
    private Projection projection;
    @Autowired
    private ReadsideCommunicator commmunicator;

    //TODO: zu einem event zusammenführen
    //Events
    @PostMapping(value = "customerCreated")
    public boolean customerCreated(@RequestBody CustomerCreatedEvent event) {
        System.out.println("Event received: " + CustomerCreatedEvent.class.getSimpleName());
        projection.processCustomerCreatedEvent(event);
        return true;
    }

    @PostMapping(value = "bookingCreated")
    public boolean bookingCreated(@RequestBody BookingCreatedEvent event) {
        System.out.println("Event received: " + BookingCreatedEvent.class.getSimpleName());
        projection.processBookingCreatedEvent(event);
        return true;
    }

    @PostMapping(value = "bookingCancelled")
    public boolean bookingCancelled(@RequestBody BookingCancelledEvent event) {
        System.out.println("Event received: " + BookingCancelledEvent.class.getSimpleName());
        projection.processBookingCancelledEvent(event);
        return true;
    }

    @PostMapping(value = "dbsDeleted")
    public boolean dbsDeleted(@RequestBody DBsDeletedEvent event) {
        System.out.println("Event received: " + DBsDeletedEvent.class.getSimpleName());
        projection.processDBsDeletedEvent(event);
        return true;
    }

    @PostMapping(value = "roomCreated")
    public boolean roomsCreated(@RequestBody RoomCreatedEvent event) {
        System.out.println("Event received: " + RoomCreatedEvent.class.getSimpleName());
        projection.processRoomCreatedEvent(event);
        return true;
    }

    //--------------------------------------------------------------------------------------------------//
    //Queries
    @GetMapping(value = "/freeRooms")
    public List<Room> getFreeRooms(@RequestParam String fromDate, @RequestParam String toDate, @RequestParam int numberOfPeople) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDateLD = LocalDate.parse(fromDate, formatter);
        LocalDate toDateLD = LocalDate.parse(toDate, formatter);
        GetFreeRoomsQuery getFreeRoomsQuery = new GetFreeRoomsQuery(fromDateLD, toDateLD, numberOfPeople);
        return projection.handleGetFreeRoomsQuery(getFreeRoomsQuery);
    }

    @GetMapping(value = "/bookings")
    public List<Booking> getBookings(@RequestParam String fromDate, @RequestParam String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDateLD = LocalDate.parse(fromDate, formatter);
        LocalDate toDateLD = LocalDate.parse(toDate, formatter);
        GetBookingsQuery getBookingsQuery = new GetBookingsQuery(fromDateLD, toDateLD);
        return projection.handleGetBookingsQuery(getBookingsQuery);
    }

    @GetMapping(value = "/customers")
    public List<Customer> getCustomers(@RequestParam(required = false) String name) {
        if (name == null) {
            GetCustomersQuery getCustomersQuery = new GetCustomersQuery();
            return projection.handleGetCustomerQuery(getCustomersQuery);
        }
        GetCustomersQuery getCustomersQuery = new GetCustomersQuery(name);
        return projection.handleGetCustomerQuery(getCustomersQuery);
    }

    @GetMapping(value = "/events")
    public List<Event> getAllEvents() {
        return commmunicator.getAllEvents();
    }
}
