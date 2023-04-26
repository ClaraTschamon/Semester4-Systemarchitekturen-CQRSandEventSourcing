package at.fhv.cts.fxclient.rest;

import at.fhv.cts.fxclient.domainModel.RemoteBooking;
import at.fhv.cts.fxclient.domainModel.RemoteCustomer;
import at.fhv.cts.fxclient.domainModel.RemoteEvent;
import at.fhv.cts.fxclient.domainModel.RemoteRoom;

import java.time.LocalDate;
import java.util.List;

public interface IRestFacade {

    List<RemoteCustomer> getCustomers(String name);

    List<RemoteBooking> getBookings(LocalDate arrivalDate, LocalDate departureDate);

    List<RemoteRoom> getFreeRooms(LocalDate fromDate, LocalDate toDate, int numberOfPeople);

    void initializeDBs();

    void restoreDBs();

    void deleteDBs();

    boolean cancelBooking(String bookingId);

    String createCustomer(String name, String address, LocalDate dateOfBirth);

    boolean bookRooms(LocalDate arrivalDate, LocalDate departureDate, List<Integer> roomNumbers, String customerId);

    List<RemoteEvent> getAllEvents();
}
