package at.fhv.cts.readside.repositories;

import share.domainModels.Customer;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CustomerReadRepository {

    Map<String, Customer> customers = new HashMap<>(); //name, customer

    public Customer getCustomerById(UUID id) {
        return customers.values().stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers.values());
    }

    public List<Customer> getCustomers(String name) {
        return customers.values().stream()
                .filter(customer -> customer.getName().contains(name))
                .collect(Collectors.toList());
    }

    public void insertCustomer(Customer customer) {
        customers.put(customer.getName(), customer);
    }

    public void delete() {
        customers.clear();
    }
}
