package at.fhv.cts.eventside;

import share.events.Event;
import at.fhv.cts.eventside.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventsideRestController {

    @Autowired
    private EventService eventService;


    @PostMapping(value = "/event", consumes = "application/json", produces = "application/json")
    public boolean addEvent(@RequestBody Event event) {
        System.out.println("Event received: " + event.getClass());
        eventService.processEvent(event);
        return true;
    }

    @GetMapping(value = "/subscribe")
    public boolean subscribe(@RequestParam String host, @RequestParam String eventUrl) {
        eventService.subscribe(host, eventUrl);
        System.out.println("Subscribed to eventbus");
        return true;
    }

    @GetMapping(value = "/allEvents")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
}
