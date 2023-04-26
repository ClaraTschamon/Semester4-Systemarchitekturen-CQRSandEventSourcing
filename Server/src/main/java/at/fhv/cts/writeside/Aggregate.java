package at.fhv.cts.writeside;

import at.fhv.cts.eventside.events.*;
import share.commands.*;
import share.domainModels.*;
import at.fhv.cts.writeside.repositories.BookingWriteRepository;
import at.fhv.cts.writeside.repositories.CustomerWriteRepository;
import at.fhv.cts.writeside.repositories.RoomWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class Aggregate {

    @Autowired
    private BookingWriteRepository bookingWriteRepository;

    @Autowired
    private CustomerWriteRepository customerWriteRepository;

    @Autowired
    private RoomWriteRepository roomWriteRepository;

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
        customerWriteRepository.createCustomer(customer);

        CustomerCreatedEvent event = new CustomerCreatedEvent(customer.getId(), customer.getName(),
                customer.getAddress(), customer.getDateOfBirth(), LocalDateTime.now());
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
            customerWriteRepository.deleteCustomer(command.getCustomerId());
            return false;
        }
        for(Integer roomNo : command.getRooms()) {
            Room room = roomWriteRepository.getRoomByNo(roomNo);
            if(room == null) {
                //delete customer
                customerWriteRepository.deleteCustomer(command.getCustomerId());
                return false;
            }

            if(!((room.getReservedFrom().isBefore(command.getArrivalDate()) || room.getReservedFrom().isEqual(command.getArrivalDate())) ||
                    room.getReservedUntil().isAfter(command.getDepartureDate()))) {
                return false;
            }
        }
        //

        //create booking
        Set<Room> rooms = new HashSet<>();
        for(Integer roomNo : command.getRooms()) {
            Room room = roomWriteRepository.getRoomByNo(roomNo);
            rooms.add(room);
        }

        Customer customer = customerWriteRepository.getCustomerById(command.getCustomerId());

        Booking booking = new Booking(UUID.randomUUID().toString(), command.getArrivalDate(), command.getDepartureDate(),
                customer, rooms);

        bookingWriteRepository.createBooking(booking);
        //

        //create event
        Set<Integer> roomNumbers = booking.getRooms().stream().map(Room::getRoomNo).collect(Collectors.toSet());
        BookingCreatedEvent event = new BookingCreatedEvent(booking.getBookingId(), booking.getFromDate(), booking.getToDate(),
                booking.getCustomer().getId(), roomNumbers, LocalDateTime.now());
        return eventPublisher.publishEvent(event);
        //
    }

    public boolean handleCancelBookingCommand(CancelBookingCommand command) {
        //validation
        Booking booking = bookingWriteRepository.getBookingById(command.getBookingId());
        if (booking == null) {
            return false;
        }
        //

        //cancel booking
        bookingWriteRepository.cancelBooking(booking.getBookingId());
        //

        //create event
        BookingCancelledEvent event = new BookingCancelledEvent(booking.getBookingId(), LocalDateTime.now());
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
        Map<UUID, Customer> customers = customerWriteRepository.initializeCustomers();
        Map<Integer, Room> rooms = roomWriteRepository.initializeRooms();
        Map<String, Booking> bookings = bookingWriteRepository.initializeBookingList();
        //

        //create events
        for (Customer customer : customers.values()) {
            CustomerCreatedEvent event = new CustomerCreatedEvent(customer.getId(), customer.getName(),
                    customer.getAddress(), customer.getDateOfBirth(),
                    LocalDateTime.now());
            eventPublisher.publishEvent(event);
        }

        for (Room room : rooms.values()) {
            RoomCreatedEvent event = new RoomCreatedEvent(room.getRoomNo(), room.getMaxPersons(), room.getCategory(),
                    LocalDateTime.now());
            eventPublisher.publishEvent(event);
        }

        for(Booking booking : bookings.values()) {
            Set<Integer> roomNumbers = booking.getRooms().stream().map(Room::getRoomNo).collect(Collectors.toSet());
            BookingCreatedEvent event = new BookingCreatedEvent(booking.getBookingId(), booking.getFromDate(), booking.getToDate(),
                    booking.getCustomer().getId(), roomNumbers, LocalDateTime.now());
            eventPublisher.publishEvent(event);
        }
        //
    }

    public void restoreDBs() {
        //create event
        DBsRestoredEvent event = new DBsRestoredEvent(LocalDateTime.now());
        eventPublisher.publishEvent(event);
        //
    }
}
