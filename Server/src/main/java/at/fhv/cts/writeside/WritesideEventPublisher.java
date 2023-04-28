package at.fhv.cts.writeside;

import share.events.Event;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WritesideEventPublisher {

    private final WebClient localApiClient = WebClient.create("http://localhost:8080"); //=eventside

    public WritesideEventPublisher() {
    }

    public <E extends Event> Boolean publishEvent(E event) {
        System.out.println(event);
        return localApiClient
                .post()
                .uri("/event/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(event),event.getClass())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}
