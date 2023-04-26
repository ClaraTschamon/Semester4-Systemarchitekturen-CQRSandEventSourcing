package at.fhv.cts.eventside.events;


import java.time.LocalDateTime;

public class BookingCancelledEvent extends Event {
    private String bookingId;

    public BookingCancelledEvent(String bookingId, LocalDateTime timestamp) {
        super(timestamp);
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        return "BookingCancelledEvent{" +
                "bookingId=" + bookingId +
                '}';
    }
}
