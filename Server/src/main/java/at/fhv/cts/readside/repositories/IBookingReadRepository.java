package at.fhv.cts.readside.repositories;

import at.fhv.cts.readside.domainModels.Booking;

import java.time.LocalDate;
import java.util.List;

public interface IBookingReadRepository {
    Booking getBookingById(String bookingId);
    List<Booking> getBookings(LocalDate arrivalDate, LocalDate departureDate);
    void cancelBooking(String bookingId);
    void addBooking(Booking booking);
    void deleteBookings();
}
