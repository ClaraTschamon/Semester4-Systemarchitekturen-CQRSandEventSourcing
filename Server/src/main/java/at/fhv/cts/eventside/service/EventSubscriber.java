package at.fhv.cts.eventside.service;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import share.events.Event;

public class EventSubscriber {

    private final WebClient client;
    private final String eventUrl;

    public EventSubscriber(String host, String eventUrl) {
        this.client = WebClient.create(host);
        this.eventUrl = eventUrl;
    }

    public void notify(Event event) {
        if (eventUrl != null) {
            client.post()
                    .uri(eventUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(event), Event.class)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        }
    }
}
