package at.fhv.cts.fxclient.rest;

import at.fhv.cts.fxclient.domainModel.RemoteBooking;
import at.fhv.cts.fxclient.domainModel.RemoteCustomer;
import at.fhv.cts.fxclient.domainModel.RemoteEvent;
import at.fhv.cts.fxclient.domainModel.RemoteRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReadsideAdapterImpl implements IReadsideAdapter {
    String readsideClientURL = "http://localhost:8082";

    private final WebClient readsideClient = WebClient.create(readsideClientURL);

    @Override
    public List<RemoteCustomer> getCustomers(String name) {
        ParameterizedTypeReference<List<RemoteCustomer>> responseType = new ParameterizedTypeReference<List<RemoteCustomer>>() {
        };

        if (name != null) {
            return readsideClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/customers/")
                            .queryParam("name", name)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(List.class)
                    .map(list -> {
                        List<RemoteCustomer> remoteCustomers = new ArrayList<>();
                        for (Object obj : list) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            RemoteCustomer remoteCustomer = objectMapper.convertValue(obj, RemoteCustomer.class);
                            remoteCustomers.add(remoteCustomer);
                        }
                        return remoteCustomers;
                    })
                    .block();
        }
        return readsideClient
                .get()
                .uri("/customers/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(List.class)
                .map(list -> {
                    List<RemoteCustomer> remoteCustomers = new ArrayList<>();
                    for (Object obj : list) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        RemoteCustomer remoteCustomer = objectMapper.convertValue(obj, RemoteCustomer.class);
                        remoteCustomers.add(remoteCustomer);
                    }
                    return remoteCustomers;
                })
                .block();
    }

    @Override
    public List<RemoteBooking> getBookings(LocalDate arrivalDate, LocalDate departureDate) {
        return readsideClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bookings/")
                        .queryParam("fromDate", arrivalDate)
                        .queryParam("toDate", departureDate)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(List.class)
                .map(list -> {
                    List<RemoteBooking> remoteBookings = new ArrayList<>();
                    for (Object obj : list) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.registerModule(new JavaTimeModule());
                        RemoteBooking remoteBooking = objectMapper.convertValue(obj, RemoteBooking.class);
                        remoteBookings.add(remoteBooking);
                    }
                    return remoteBookings;
                })
                .block();
    }

    @Override
    public List<RemoteRoom> getFreeRooms(LocalDate fromDate, LocalDate toDate, int numberOfPeople) {
        return readsideClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/freeRooms/")
                        .queryParam("fromDate", fromDate)
                        .queryParam("toDate", toDate)
                        .queryParam("numberOfPeople", numberOfPeople)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(List.class)
                .map(list -> {
                    List<RemoteRoom> remoteRooms = new ArrayList<>();
                    for (Object obj : list) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.registerModule(new JavaTimeModule());
                        RemoteRoom remoteRoom = objectMapper.convertValue(obj, RemoteRoom.class);
                        remoteRooms.add(remoteRoom);
                    }
                    return remoteRooms;
                })
                .block();
    }

    @Override
    public List<RemoteEvent> getAllEvents() {
        return readsideClient
                .get()
                .uri("/events/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(List.class)
                .map(list -> {
                    List<RemoteEvent> events = new ArrayList<>();
                    for (Object obj : list) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.registerModule(new JavaTimeModule());
                        RemoteEvent event = objectMapper.convertValue(obj, RemoteEvent.class);
                        events.add(event);
                    }
                    return events;
                })
                .block();
    }
}
