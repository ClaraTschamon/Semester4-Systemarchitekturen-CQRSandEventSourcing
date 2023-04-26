package at.fhv.cts.fxclient.rest;

import java.time.LocalDate;
import java.util.List;

public interface IWritesideAdapter {
    void initializeDBs();
    void restoreDBs();
    void deleteDBs();
    boolean cancelBooking(String bookingId);
    String createCustomer(String name, String address, LocalDate dateOfBirth); //returns customerId
    boolean bookRooms(LocalDate arrivalDate, LocalDate departureDate,
                      List<Integer> roomNumbers, String customerId);

}
