package share.events;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class BookingCreatedEvent extends Event{
    private String bookingId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private UUID customerId;
    private Set<Integer> rooms;

    public BookingCreatedEvent() {
        super(LocalDateTime.now());
    }

    public BookingCreatedEvent(String bookingId, LocalDate fromDate, LocalDate toDate,
                               UUID customerId, Set<Integer> rooms) { //called in writeside
        super(LocalDateTime.now());
        this.bookingId = bookingId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.customerId = customerId;
        this.rooms = rooms;
    }

    //for eventside when reading out events from file
    public BookingCreatedEvent(String bookingId, LocalDate fromDate, LocalDate toDate,
                               UUID customerId, Set<Integer> rooms, LocalDateTime timestamp) {
        super(timestamp);
        this.bookingId = bookingId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.customerId = customerId;
        this.rooms = rooms;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Set<Integer> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Integer> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "BookingCreatedEvent{" +
                "bookingId=" + bookingId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", customerId=" + customerId +
                ", rooms=" + rooms +
                '}';
    }
}
