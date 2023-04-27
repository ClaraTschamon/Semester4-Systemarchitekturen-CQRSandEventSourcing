package at.fhv.cts.fxclient.rest;

import at.fhv.cts.fxclient.domainModel.RemoteBooking;
import at.fhv.cts.fxclient.domainModel.RemoteCustomer;
import at.fhv.cts.fxclient.domainModel.RemoteEvent;
import at.fhv.cts.fxclient.domainModel.RemoteRoom;

import java.time.LocalDate;
import java.util.List;

public class RestFacadeImpl implements IRestFacade {

    private IReadsideAdapter readsideAdapter;

    private IWritesideAdapter writesideAdapter;

    public RestFacadeImpl(IReadsideAdapter readsideAdapter, IWritesideAdapter writesideAdapter) {
        this.readsideAdapter = readsideAdapter;
        this.writesideAdapter = writesideAdapter;
    }


    @Override
    public List<RemoteCustomer> getCustomers(String name) {
        return readsideAdapter.getCustomers(name);
    }

    @Override
    public List<RemoteBooking> getBookings(LocalDate arrivalDate, LocalDate departureDate) {
        return readsideAdapter.getBookings(arrivalDate, departureDate);
    }

    @Override
    public List<RemoteRoom> getFreeRooms(LocalDate fromDate, LocalDate toDate, int numberOfPeople) {
        return readsideAdapter.getFreeRooms(fromDate, toDate, numberOfPeople);
    }

    @Override
    public void initializeDBs() {
        writesideAdapter.initializeDBs();
    }

    @Override
    public void restoreDBs() {
        writesideAdapter.restoreDBs();
    }

    @Override
    public void deleteDBs() {
        writesideAdapter.deleteDBs();
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        return writesideAdapter.cancelBooking(bookingId);
    }

    @Override
    public String createCustomer(String name, String address, LocalDate dateOfBirth) {
        return writesideAdapter.createCustomer(name, address, dateOfBirth);
    }

    @Override
    public boolean bookRooms(LocalDate arrivalDate, LocalDate departureDate, List<Integer> roomNumbers, String customerId) {
        return writesideAdapter.bookRooms(arrivalDate, departureDate, roomNumbers, customerId);
    }

    @Override
    public List<RemoteEvent> getAllEvents() {
        return readsideAdapter.getAllEvents();
    }
}
