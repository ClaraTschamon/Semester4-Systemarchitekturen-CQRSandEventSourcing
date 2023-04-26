package at.fhv.cts.readside.queries;

import java.time.LocalDate;

public class GetBookingsQuery {

    private LocalDate fromDate;
    private LocalDate toDate;

    public GetBookingsQuery(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
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
}
