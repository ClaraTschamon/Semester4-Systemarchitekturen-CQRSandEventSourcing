package at.fhv.cts.writeside;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import share.commands.BookRoomsCommand;
import share.commands.CancelBookingCommand;
import share.commands.CreateCustomerCommand;

@RestController
public class WritesideRestController {

    @Autowired
    private Aggregate aggregate;

    //für commands 3 verschiedene methoden weil createCustomer() String returned und die anderen boolean
    @PostMapping(value = "/createCustomer")
    public String createCustomer(@RequestBody CreateCustomerCommand command) {
        return aggregate.handleCreateCustomerCommand(command);
    }

    @PostMapping(value = "/bookRooms")
    public boolean bookRooms(@RequestBody BookRoomsCommand command) {
        return aggregate.handleBookRoomCommand(command);
    }

    @PostMapping(value = "/cancelBooking")
    public boolean cancelBooking(@RequestBody CancelBookingCommand command) {
        return aggregate.handleCancelBookingCommand(command);
    }

    @PostMapping(value = "/deleteDBs")
    public void deleteDBs() { //der command wird nur auf das query modell weitergeleitet
        aggregate.deleteDBs();
    }

    @PostMapping(value = "/initializeDBs")
    public void initializeDBs() { //der command wird sowohl auf dem writemodell ausgeführt als auch auf das query modell weitergeleitet
        aggregate.initializeDBs();
    }

    @PostMapping(value = "/restoreDBs")
    public void restoreDBs() { //der command wird nur auf das query modell weitergeleitet
        aggregate.restoreDBs();
    }
}
