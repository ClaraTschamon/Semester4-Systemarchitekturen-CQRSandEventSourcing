package at.fhv.cts.writeside;

import share.commands.*;
import share.domainModels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.events.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class Aggregate {

    @Autowired
    IRepositoryFacade repositoryFacade;

    @Autowired
    private WritesideEventPublisher eventPublisher;


    public String handleCreateCustomerCommand(CreateCustomerCommand command) {
        //validation
        if(command.getName() == null || command.getAddress() == null || command.getBirthdate() == null) {
            return null;
        }
        //

        //create customer
        Customer customer = new Customer(UUID.randomUUID(), command.getName(), command.getAddress(), command.getBirthdate());
        repositoryFacade.createCustomer(customer);

        CustomerCreatedEvent event = new CustomerCreatedEvent(customer.getId(), customer.getName(),
                customer.getAddress(), customer.getDateOfBirth());
        //

        //create event
        eventPublisher.publishEvent(event);
        return customer.getId().toString();
        //
    }

    public boolean handleBookRoomCommand(BookRoomsCommand command) {
        //validation
        if(command.getRooms().isEmpty() || command.getArrivalDate().isBefore(LocalDate.now()) ||
            command.getDepartureDate().isBefore(command.getArrivalDate()) || command.getCustomerId() == null) {
            //delete customer (was already created in handleCreateCustomerCommand)
            repositoryFacade.deleteCustomer(command.getCustomerId());
            return false;
        }
        for(Integer roomNo : command.getRooms()) {
            Room room = repositoryFacade.getRoomByNo(roomNo);
            if(room == null) {
                //delete customer
                repositoryFacade.deleteCustomer(command.getCustomerId());
                return false;
            }
            if(!(room.getReservedFrom() == null && room.getReservedUntil() == null)) {
                if (!((room.getReservedFrom().isBefore(command.getArrivalDate()) || room.getReservedFrom().isEqual(command.getArrivalDate())) ||
                        room.getReservedUntil().isAfter(command.getDepartureDate()))) {
                    return false;
                }
            }
        }
        //

        //create booking
        Set<Room> rooms = new HashSet<>();
        for(Integer roomNo : command.getRooms()) {
            Room room = repositoryFacade.getRoomByNo(roomNo);
            rooms.add(room);
        }

        Customer customer = repositoryFacade.getCustomerById(command.getCustomerId());

        Booking booking = new Booking(UUID.randomUUID().toString(), command.getArrivalDate(), command.getDepartureDate(),
                customer, rooms);

        repositoryFacade.createBooking(booking);
        //

        //create event
        Set<Integer> roomNumbers = booking.getRooms().stream().map(Room::getRoomNo).collect(Collectors.toSet());
        BookingCreatedEvent event = new BookingCreatedEvent(booking.getBookingId(), booking.getFromDate(), booking.getToDate(),
                booking.getCustomer().getId(), roomNumbers);
        return eventPublisher.publishEvent(event);
        //
    }

    public boolean handleCancelBookingCommand(CancelBookingCommand command) {
        //validation
        Booking booking = repositoryFacade.getBookingById(command.getBookingId());
        if (booking == null) {
            return false;
        }
        //

        //cancel booking
        repositoryFacade.cancelBooking(booking.getBookingId());
        //

        //make rooms free
       for(Room room : booking.getRooms()) {
           repositoryFacade.freeRoom(room.getRoomNo());
       }
       //

        //create event
        BookingCancelledEvent event = new BookingCancelledEvent(booking.getBookingId());
        return eventPublisher.publishEvent(event);
        //
    }

    public void deleteDBs() {
        //create event
        DBsDeletedEvent event = new DBsDeletedEvent();
        eventPublisher.publishEvent(event);
        //
    }

    public void initializeDBs() {
        //initialize DBs
        Map<UUID, Customer> customers = repositoryFacade.initializeCustomers();
        Map<Integer, Room> rooms = repositoryFacade.initializeRooms();
        Map<String, Booking> bookings = repositoryFacade.initializeBookingList();
        //

        //create events
        for (Customer customer : customers.values()) {
            CustomerCreatedEvent event = new CustomerCreatedEvent(customer.getId(), customer.getName(),
                    customer.getAddress(), customer.getDateOfBirth());
            eventPublisher.publishEvent(event);
        }

        for (Room room : rooms.values()) {
            RoomCreatedEvent event = new RoomCreatedEvent(room.getRoomNo(), room.getMaxPersons(), room.getCategory(), null, null);
            eventPublisher.publishEvent(event);
        }

        for(Booking booking : bookings.values()) {
            Set<Integer> roomNumbers = booking.getRooms().stream().map(Room::getRoomNo).collect(Collectors.toSet());
            BookingCreatedEvent event = new BookingCreatedEvent(booking.getBookingId(), booking.getFromDate(), booking.getToDate(),
                    booking.getCustomer().getId(), roomNumbers);
            eventPublisher.publishEvent(event);
        }
        //
    }

    public void restoreDBs() {
        //create event
        DBsRestoredEvent event = new DBsRestoredEvent();
        eventPublisher.publishEvent(event);
        //
    }
}
