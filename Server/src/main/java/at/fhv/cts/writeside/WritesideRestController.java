package at.fhv.cts.writeside;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import share.commands.BookRoomsCommand;
import share.commands.CancelBookingCommand;
import share.commands.CreateCustomerCommand;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
public class WritesideRestController {

    @Autowired
    private Aggregate aggregate;

    /*@GetMapping(value = "/createCustomer") //TODO: in POST umwandeln
    public String createCustomer(@RequestParam String name, @RequestParam String address, @RequestParam String dateOfBirth) { //take request parameters form website
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdateLD = LocalDate.parse(dateOfBirth, formatter);
        CreateCustomerCommand command = new CreateCustomerCommand(name, address, birthdateLD);
        return aggregate.handleCreateCustomerCommand(command);
    }*/


    @PostMapping(value = "/createCustomer")
    public String createCustomer(@RequestBody CreateCustomerCommand command) { //dann kann es einen übergeordneten command geben und nur "/create" URL
        return aggregate.handleCreateCustomerCommand(command);
    }


    @GetMapping(value = "/bookRooms") //TODO: in POST umwandeln
    public boolean bookRooms(@RequestParam String arrivalDate, @RequestParam String departureDate,
                             @RequestParam List<Integer> roomNumbers, @RequestParam String customerId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate arrivalDateLD = LocalDate.parse(arrivalDate, formatter);
        LocalDate departureDateLD = LocalDate.parse(departureDate, formatter);
        BookRoomsCommand bookRoomsCommand = new BookRoomsCommand(arrivalDateLD, departureDateLD, roomNumbers, UUID.fromString(customerId));
        return aggregate.handleBookRoomCommand(bookRoomsCommand);
    }

    @GetMapping(value = "/cancelBooking") //TODO: in POST umwandeln
    public boolean cancelBooking(@RequestParam String bookingId) {
        System.out.println("cancelBooking called");
        CancelBookingCommand cancelBookingCommand = new CancelBookingCommand(bookingId);
        return aggregate.handleCancelBookingCommand(cancelBookingCommand);
    }

    @PostMapping(value = "/deleteDBs")
    public void deleteDBs() { //der command wird nur auf das query modell weitergeleitet
        aggregate.deleteDBs();
        System.out.println("deleteDBs called");
    }

    @PostMapping(value = "/initializeDBs")
    public void initializeDBs() { //der command wird sowohl auf dem writemodell ausgeführt als auch auf das query modell weitergeleitet
        aggregate.initializeDBs();
    }

    @PostMapping(value = "/restoreDBs")
    public void restoreDBs() { //der command wird nur auf das query modell weitergeleitet
        aggregate.restoreDBs();
        System.out.println("restoreDBs called");
    }
}
