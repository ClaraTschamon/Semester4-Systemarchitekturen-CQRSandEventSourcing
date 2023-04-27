package at.fhv.cts.writeside.repositories;

import share.domainModels.Customer;

import java.util.Map;
import java.util.UUID;

public interface ICustomerWriteRepository {
    Map<UUID, Customer> initializeCustomers();

    void createCustomer(Customer customer);

    Customer getCustomerById(String id);

    Customer getRandomCustomer(); //nur um bookings zu initialisieren in bookingWriteRepository

    void deleteCustomer(String customerId);
}
