package at.fhv.cts.readside;

import share.domainModels.*;

import at.fhv.cts.readside.queries.GetBookingsQuery;
import at.fhv.cts.readside.queries.GetCustomersQuery;
import at.fhv.cts.readside.queries.GetFreeRoomsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import share.events.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class ReadsideRestController {

    @Autowired
    private Projection projection;
    @Autowired
    private ReadsideCommunicator commmunicator;


    //Events
    @PostMapping(value = "event")
    public boolean event(@RequestBody Event event) {
        System.out.println("Event received: " + event.getClass().getSimpleName());
        projection.processEvent(event);
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
