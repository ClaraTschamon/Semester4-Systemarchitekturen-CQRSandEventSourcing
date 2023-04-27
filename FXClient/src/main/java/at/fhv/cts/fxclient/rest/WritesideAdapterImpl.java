package at.fhv.cts.fxclient.rest;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import share.commands.BookRoomsCommand;
import share.commands.CancelBookingCommand;
import share.commands.CreateCustomerCommand;

import java.time.LocalDate;
import java.util.List;

public class WritesideAdapterImpl implements IWritesideAdapter {

    String writesideClientURL = "http://localhost:8081";
    private final WebClient writesideClient = WebClient.create(writesideClientURL);

    @Override
    public void initializeDBs() {
        writesideClient
                .post()
                .uri("/initializeDBs")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void restoreDBs() {
        writesideClient
                .post()
                .uri("/restoreDBs")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void deleteDBs() {
        writesideClient
                .post()
                .uri("/deleteDBs")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        CancelBookingCommand command = new CancelBookingCommand(bookingId);

        return writesideClient
                .post()
                .uri("/cancelBooking")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(command)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    @Override
    public String createCustomer(String name, String address, LocalDate dateOfBirth) { //returns customerId
        CreateCustomerCommand command = new CreateCustomerCommand(name, address, dateOfBirth);
        return writesideClient
                .post()
                .uri("/createCustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(command)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public boolean bookRooms(LocalDate arrivalDate, LocalDate departureDate,
                             List<Integer> roomNumbers, String customerId) {
        BookRoomsCommand command = new BookRoomsCommand(arrivalDate, departureDate, roomNumbers, customerId);
        return writesideClient
                .post()
                .uri("/bookRooms")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(command)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}
