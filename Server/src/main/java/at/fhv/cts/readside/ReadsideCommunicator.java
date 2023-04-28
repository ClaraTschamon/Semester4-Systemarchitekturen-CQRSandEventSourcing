package at.fhv.cts.readside;

import share.events.Event;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class ReadsideCommunicator {
    private final WebClient localApiClient = WebClient.create("http://localhost:8080"); //=eventside

    public ReadsideCommunicator() {
    }

    public List<Event> getAllEvents() {
        return localApiClient
                .get()
                .uri("/allEvents/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }
}
