package at.fhv.cts.fxclient.rest;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
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
        return writesideClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cancelBooking")
                        .queryParam("bookingId", bookingId)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    @Override
    public String createCustomer(String name, String address, LocalDate dateOfBirth) { //returns customerId
        CreateCustomerCommand command = new CreateCustomerCommand(name, address, dateOfBirth);
        /*return writesideClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/createCustomer")
                        .queryParam("name", name)
                        .queryParam("address", address)
                        .queryParam("dateOfBirth", dateOfBirth)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();*/
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



    /* //TODO rewrite createCustomer to post
     WebClient webClient = WebClient.create();
    return writesideClient.post()
            .uri("/createCustomer?name={name}&address={address}&dateOfBirth={dateOfBirth}", name, address, dateOfBirth)
            .retrieve()
            .bodyToMono(String.class)
            .block();

     */


    @Override
    public boolean bookRooms(LocalDate arrivalDate, LocalDate departureDate,
                             List<Integer> roomNumbers, String customerId) {
        return writesideClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bookRooms")
                        .queryParam("arrivalDate", arrivalDate)
                        .queryParam("departureDate", departureDate)
                        .queryParam("roomNumbers", roomNumbers)
                        .queryParam("customerId", customerId)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

}
