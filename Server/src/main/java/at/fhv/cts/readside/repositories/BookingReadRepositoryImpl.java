package at.fhv.cts.readside.repositories;

import at.fhv.cts.readside.domainModels.Booking;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BookingReadRepositoryImpl implements IBookingReadRepository {
    private Map<String, Booking> bookings = new HashMap<>();

    @Override
    public Booking getBookingById(String bookingId) {
        return bookings.get(bookingId);
    }

    @Override
    public List<Booking> getBookings(LocalDate arrivalDate, LocalDate departureDate) {
        //minusDays(1) and plusDays(1) because I want the bookins which are after of equal arrival date and before of equal departure date
        return bookings.values().stream()
                .filter(booking -> booking.getFromDate()
                .isAfter(arrivalDate.minusDays(1)) && booking.getToDate()
                .isBefore(departureDate.plusDays(1)))
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(String bookingId) {
        bookings.remove(bookingId);
    }

    @Override
    public void addBooking(Booking booking) {
        bookings.put(booking.getBookingId(), booking);
    }

    @Override
    public void deleteBookings() {
        bookings.clear();
    }
}
