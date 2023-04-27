package at.fhv.cts.readside.repositories;

import share.domainModels.Customer;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CustomerReadRepositoryImpl implements ICustomerReadRepository{

    Map<String, Customer> customers = new HashMap<>(); //name, customer

    @Override
    public Customer getCustomerById(UUID id) {
        return customers.values().stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Customer> getCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public List<Customer> getCustomers(String name) {
        return customers.values().stream()
                .filter(customer -> customer.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.put(customer.getName(), customer);
    }

    @Override
    public void deleteCustomer() {
        customers.clear();
    }
}
