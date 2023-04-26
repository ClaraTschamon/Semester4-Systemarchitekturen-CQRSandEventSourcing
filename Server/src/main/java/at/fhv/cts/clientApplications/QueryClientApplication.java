package at.fhv.cts.clientApplications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@Configuration
@ComponentScan("at.fhv.cts.readside")
public class QueryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryClientApplication.class, args);

        subscribeAtEventBus();
    }

    private static void subscribeAtEventBus() {

        WebClient client = WebClient.create("http://localhost:8080"); //8080 = eventside

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/subscribe")
                        .queryParam("host", "http://localhost:8082")
                        .queryParam("eventUrl", "/event/")
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

    }
}
