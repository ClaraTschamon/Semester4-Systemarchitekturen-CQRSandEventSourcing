package at.fhv.cts.writeside.repositories;

import share.domainModels.Booking;

import java.util.Map;

public interface IBookingWriteRepository {
    void createBooking(Booking booking);

    Booking getBookingById(String id);

    void cancelBooking(String bookingId);

    Map<String, Booking> initializeBookingList();
}
