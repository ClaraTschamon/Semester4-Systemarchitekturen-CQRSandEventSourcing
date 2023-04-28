package at.fhv.cts.writeside.repositories;

import at.fhv.cts.writeside.domainModels.Booking;

import java.util.Map;

public interface IBookingWriteRepository {
    void createBooking(Booking booking);

    Booking getBookingById(String id);

    void cancelBooking(String bookingId);

    Map<String, Booking> initializeBookingList();

    Map<String, Booking> getAllBookings();
}
