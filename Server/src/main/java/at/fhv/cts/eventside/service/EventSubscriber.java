package at.fhv.cts.eventside.service;

import at.fhv.cts.eventside.events.*;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class EventSubscriber {

    private final WebClient client;
    private final String createdCustomerUrl;
    private final String createdBookingUrl;
    private final String cancelledBookingUrl;
    private final String dbsDeletedUrl;
    private final String roomCreatedUrl;

    public EventSubscriber(String host, String createdCustomerUrl, String createdBookingUrl, String cancelledBookingUrl, String dbsDeletedUrl, String roomCreatedUrl) {
        this.client = WebClient.create(host);
        this.createdCustomerUrl = createdCustomerUrl;
        this.createdBookingUrl = createdBookingUrl;
        this.cancelledBookingUrl = cancelledBookingUrl;
        this.dbsDeletedUrl = dbsDeletedUrl;
        this.roomCreatedUrl = roomCreatedUrl;
    }

    public void notify(Event event) {
        String url = null;
        if(event instanceof CustomerCreatedEvent) {
            url = createdCustomerUrl;
        } else if(event instanceof BookingCreatedEvent) {
            url = createdBookingUrl;
        } else if(event instanceof BookingCancelledEvent) {
            url = cancelledBookingUrl;
        } else if(event instanceof DBsDeletedEvent) {
            url = dbsDeletedUrl;
        } else if(event instanceof RoomCreatedEvent) {
            url = roomCreatedUrl;
        }

        if (url != null) {
            client.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(event), Event.class)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        }
    }
}
