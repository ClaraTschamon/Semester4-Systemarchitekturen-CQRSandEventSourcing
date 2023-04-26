package at.fhv.cts.readside.queries;

import java.time.LocalDate;

public class GetFreeRoomsQuery {

    private LocalDate fromDate;
    private LocalDate toDate;
    private int nrOfPersons;

    public GetFreeRoomsQuery(LocalDate fromDate, LocalDate toDate, int nrOfPersons) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nrOfPersons = nrOfPersons;
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

    public int getNrOfPersons() {
        return nrOfPersons;
    }

    public void setNrOfPersons(int nrOfPersons) {
        this.nrOfPersons = nrOfPersons;
    }
}
