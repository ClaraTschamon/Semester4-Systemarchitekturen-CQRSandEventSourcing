package at.fhv.cts.writeside.domainModels;

import java.time.LocalDate;
import java.util.Set;

public class Booking {
    private String bookingId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Customer customer;
    private Set<Room> rooms;

    public Booking(String bookingId, LocalDate fromDate, LocalDate toDate, Customer customer, Set<Room> rooms) {
        this.bookingId = bookingId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }
}
