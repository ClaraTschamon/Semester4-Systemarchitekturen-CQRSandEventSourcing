package at.fhv.cts.fxclient.rest;

import at.fhv.cts.fxclient.domainModel.RemoteBooking;
import at.fhv.cts.fxclient.domainModel.RemoteCustomer;
import at.fhv.cts.fxclient.domainModel.RemoteEvent;
import at.fhv.cts.fxclient.domainModel.RemoteRoom;

import java.time.LocalDate;
import java.util.List;

public interface IReadsideAdapter {
    List<RemoteCustomer> getCustomers(String name);

    List<RemoteBooking> getBookings(LocalDate arrivalDate, LocalDate departureDate);

    List<RemoteRoom> getFreeRooms(LocalDate fromDate, LocalDate toDate, int numberOfPeople);

    List<RemoteEvent> getAllEvents();
}
