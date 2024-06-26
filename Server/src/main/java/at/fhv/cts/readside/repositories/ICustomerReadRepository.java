package at.fhv.cts.readside.repositories;


import at.fhv.cts.readside.domainModels.Customer;

import java.util.List;
import java.util.UUID;

public interface ICustomerReadRepository {
    Customer getCustomerById(UUID id);
    List<Customer> getCustomers();
    List<Customer> getCustomers(String name);
    void insertCustomer(Customer customer);
    void deleteCustomer();
}
