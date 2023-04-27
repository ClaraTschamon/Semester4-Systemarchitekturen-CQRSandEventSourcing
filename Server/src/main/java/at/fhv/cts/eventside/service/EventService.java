package at.fhv.cts.eventside.service;

import at.fhv.cts.eventside.events.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EventService {

    private Map<String, EventSubscriber> subscribers = new HashMap<>();


    public void processEvent(Event event) {

        if (!(event instanceof DBsDeletedEvent)) {
            writeEvent(event);

        }
        for (EventSubscriber subscriber : subscribers.values()) {
            subscriber.notify(event);
        }

        if (event instanceof DBsRestoredEvent) {
            restoreEvents();
        }
    }

    private void writeEvent(Event event) {
        File jsonFile = new File("Server/src/main/java/at/fhv/cts/eventside/service/events.json");

        // read the existing JSON data from the file
        JSONArray existingJsonArr = new JSONArray();
        if (jsonFile.exists() && jsonFile.length() != 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
                existingJsonArr = new JSONArray(new JSONTokener(reader));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject eventDetails = new JSONObject();
        eventDetails.put("eventType", event.getClass().getSimpleName());
        eventDetails.put("data", new JSONObject(event));

        // append the new event to the existing JSON data
        existingJsonArr.put(eventDetails);

        // write the updated JSON data back to the file
        try (FileWriter writer = new FileWriter(jsonFile)) {
            String jsonString = existingJsonArr.toString(4); // indentation of 4 spaces
            String[] lines = jsonString.split("\\r?\\n");
            for (String line : lines) {
                writer.write(line);
                writer.write(System.lineSeparator()); // add line separator after each entry
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getAllEvents() {
        return restoreEvents();
    }

    public List<Event> restoreEvents() { //returns list for getAllEvents()
        List<Event> events = new ArrayList<>();
        try {
            // Read the JSON file into a string
            String jsonString = new String(Files.readAllBytes(Paths.get("Server/src/main/java/at/fhv/cts/eventside/service/events.json")));

            // Parse the JSON string into a JSONArray
            JSONArray eventsArray = new JSONArray(jsonString);

            // Iterate through the events array and create the respective events
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject eventObject = eventsArray.getJSONObject(i);
                String eventType = eventObject.getString("eventType");
                JSONObject eventData = eventObject.getJSONObject("data");

                switch (eventType) {
                    case "CustomerCreatedEvent": {
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                        UUID customerId = UUID.fromString(eventData.getString("customerId"));
                        String name = eventData.getString("name");
                        String address = eventData.getString("address");
                        LocalDate birthdate = LocalDate.parse(eventData.getString("dateOfBirth"), formatter);
                        LocalDateTime timestamp = LocalDateTime.parse(eventData.getString("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent(customerId, name, address, birthdate, timestamp);
                        events.add(customerCreatedEvent);

                        for (EventSubscriber subscriber : subscribers.values()) {
                            subscriber.notify(customerCreatedEvent);
                        }
                        break;
                    }
                    case "BookingCreatedEvent": {
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                        String bookingId = eventData.getString("bookingId");
                        LocalDate fromDate = LocalDate.parse(eventData.getString("fromDate"), formatter);
                        LocalDate toDate = LocalDate.parse(eventData.getString("toDate"), formatter);
                        UUID customerId = UUID.fromString(eventData.getString("customerId"));
                        JSONArray roomsArray = eventData.getJSONArray("rooms");
                        Set<Integer> roomSet = new HashSet<>();
                        for (int j = 0; j < roomsArray.length(); j++) {
                            roomSet.add(roomsArray.getInt(j));
                        }
                        LocalDateTime timestamp = LocalDateTime.parse(eventData.getString("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        BookingCreatedEvent bookingCreatedEvent = new BookingCreatedEvent(bookingId, fromDate, toDate, customerId, roomSet, timestamp);
                        events.add(bookingCreatedEvent);

                        for (EventSubscriber subscriber : subscribers.values()) {
                            subscriber.notify(bookingCreatedEvent);
                        }
                        break;
                    }
                    case "BookingCancelledEvent": {
                        String bookingId = eventData.getString("bookingId");
                        LocalDateTime timestamp = LocalDateTime.parse(eventData.getString("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        BookingCancelledEvent bookingCancelledEvent = new BookingCancelledEvent(bookingId, timestamp);
                        events.add(bookingCancelledEvent);

                        for (EventSubscriber subscriber : subscribers.values()) {
                            subscriber.notify(bookingCancelledEvent);
                        }
                        break;
                    }
                    case "RoomCreatedEvent": {
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                        int roomNumber = eventData.getInt("roomNo");
                        int maxPersons = eventData.getInt("maxPersons");
                        String category = eventData.getString("category");
                        LocalDateTime timestamp = LocalDateTime.parse(eventData.getString("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        LocalDate reservedFrom;
                        try { //because rooms are initialized with reservedFrom = null and then reservedFrom is not written to json file
                            if (eventData.getString("reservedFrom") == null) {
                                reservedFrom = null;
                            } else {
                                reservedFrom = LocalDate.parse(eventData.getString("reservedFrom"), formatter);
                            }

                        } catch (JSONException e) {
                            reservedFrom = null;
                        }
                        LocalDate reservedUntil;
                        try {
                            if (eventData.getString("reservedUntil") == null) {
                                reservedUntil = null;
                            } else {
                                reservedUntil = LocalDate.parse(eventData.getString("reservedUntil"), formatter);
                            }
                        } catch (JSONException e) {
                            reservedUntil = null;
                        }
                        RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent(roomNumber, maxPersons, category, timestamp, reservedFrom, reservedUntil);
                        events.add(roomCreatedEvent);
                        for (EventSubscriber subscriber : subscribers.values()) {
                            subscriber.notify(roomCreatedEvent);
                        }
                        break;
                    }
                    case "DBsRestoredEvent": {
                        LocalDateTime timestamp = LocalDateTime.parse(eventData.getString("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        DBsRestoredEvent dbsRestoredEvent = new DBsRestoredEvent(timestamp);
                        events.add(dbsRestoredEvent);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return events;
    }


    public void subscribe(String host, String eventUrl) {
        EventSubscriber subscriber = new EventSubscriber(host, eventUrl);
        subscribers.put(host, subscriber);
    }
}
