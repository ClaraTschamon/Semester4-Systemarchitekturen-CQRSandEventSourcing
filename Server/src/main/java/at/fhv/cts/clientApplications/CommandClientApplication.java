package at.fhv.cts.clientApplications;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan("at.fhv.cts.writeside")
public class CommandClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandClientApplication.class, args);
    }
}
