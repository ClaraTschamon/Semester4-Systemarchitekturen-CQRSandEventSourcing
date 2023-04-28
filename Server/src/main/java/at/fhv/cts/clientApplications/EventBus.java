package at.fhv.cts.clientApplications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@SpringBootApplication
@Configuration
@ComponentScan("at.fhv.cts.eventside")
public class EventBus {
    public static void main(String[] args) {
        SpringApplication.run(EventBus.class, args);

        emptyEventsFile();
    }

    //deletes content and not file itself
    private static void emptyEventsFile() {
        try {
            PrintWriter pw = new PrintWriter("Server/src/main/java/at/fhv/cts/eventside/service/events.json");
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
