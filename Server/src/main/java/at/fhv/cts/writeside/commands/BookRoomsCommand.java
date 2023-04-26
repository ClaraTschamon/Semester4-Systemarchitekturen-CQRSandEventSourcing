package at.fhv.cts.writeside.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class BookRoomsCommand {

    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private List<Integer> rooms;
    private UUID customerId;

    public BookRoomsCommand(LocalDate arrivalDate, LocalDate departureDate, List<Integer> rooms, UUID customerId) {
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.rooms = rooms;
        this.customerId = customerId;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public List<Integer> getRooms() {
        return rooms;
    }

    public void setRooms(List<Integer> rooms) {
        this.rooms = rooms;
    }

    public String getCustomerId() {
        return customerId.toString();
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
}
