package share.commands;

public class CancelBookingCommand {
    private String bookingId;

    public CancelBookingCommand () {} //otherwise com.fasterxml.jackson.databind.exc.MismatchedInputException
    public CancelBookingCommand(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
