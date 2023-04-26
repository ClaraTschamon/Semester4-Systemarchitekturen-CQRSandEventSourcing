package at.fhv.cts.eventside.events;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerCreatedEvent extends Event {
    private UUID customerId;
    private String name;
    private String address;
    private LocalDate dateOfBirth;

    public CustomerCreatedEvent(UUID customerId, String name, String address, LocalDate birthdate,
                                LocalDateTime timestamp) {
        super(timestamp);
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.dateOfBirth = birthdate;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "CustomerCreatedEvent{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
