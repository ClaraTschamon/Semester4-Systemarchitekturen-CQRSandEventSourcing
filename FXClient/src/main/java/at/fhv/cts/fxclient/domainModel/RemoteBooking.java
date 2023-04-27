package at.fhv.cts.fxclient.domainModel;

import java.time.LocalDate;
import java.util.Set;

public class RemoteBooking {

    private String bookingId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private RemoteCustomer customer;
    private Set<RemoteRoom> rooms;

    public RemoteBooking() { //necessary for jackson databind library
        super();
    } //necessary for jackson databind library

    public RemoteBooking(String bookingId, LocalDate fromDate, LocalDate toDate, RemoteCustomer customer, Set<RemoteRoom> rooms) {
        this.bookingId = bookingId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.customer = customer;
        this.rooms = rooms;
    }

    //getter and setter needed for fx tableview
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

    public RemoteCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(RemoteCustomer customer) {
        this.customer = customer;
    }

    public Set<RemoteRoom> getRooms() {
        return rooms;
    }

    public void setRooms(Set<RemoteRoom> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "RemoteBooking{" +
                "bookingId='" + bookingId + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", customer=" + customer +
                ", rooms=" + rooms.toString() +
                '}';
    }
}
