package at.fhv.cts.writeside.repositories;

import share.domainModels.Customer;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class CustomerWriteRepository implements ICustomerWriteRepository {

    private Map<UUID, Customer> customers;

    @Override
    public Map<UUID, Customer> initializeCustomers() {
        List<Customer> myCustomers = new ArrayList<>();
        customers = new HashMap<>();

        myCustomers.add(new Customer(UUID.randomUUID(), "Max Mustermann", "Musterstrasse 1", LocalDate.of(1991, 1, 1)));
        myCustomers.add(new Customer(UUID.randomUUID(), "Linda Musterfrau", "Musterstrasse 2", LocalDate.of(1992, 2, 2)));
        myCustomers.add(new Customer(UUID.randomUUID(), "Hans Muster", "Musterstrasse 3", LocalDate.of(1993, 3, 3)));
        myCustomers.add(new Customer(UUID.randomUUID(), "Franz Muster", "Musterstrasse 4", LocalDate.of(1994, 4, 4)));
        myCustomers.add(new Customer(UUID.randomUUID(), "Hans Musterfrau", "Musterstrasse 5", LocalDate.of(1995, 5, 5)));

        for (Customer customer : myCustomers) {
            customers.put(customer.getId(), customer);
        }

        return customers;
    }

    @Override
    public void createCustomer(Customer customer) {
        customers.putIfAbsent(customer.getId(), customer);
    }

    @Override
    public Customer getCustomerById(String id) {
        return customers.get(UUID.fromString(id));
    }

    @Override
    public Customer getRandomCustomer() { //nur um bookings zu initialisieren in bookingWriteRepository
        Random random = new Random();
        int randomIndex = random.nextInt(customers.size());
        List<Customer> customerList = new ArrayList<>(customers.values());
        return customerList.get(randomIndex);
    }

    @Override
    public void deleteCustomer(String customerId) {
        customers.remove(UUID.fromString(customerId));
    }
}
