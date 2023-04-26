package at.fhv.cts.clientApplications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan("at.fhv.cts.eventside")
public class EventBus {

    public static void main(String[] args) {
        SpringApplication.run(EventBus.class, args);
    }
}
