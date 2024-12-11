package me.nzuguem.notify.services.dao;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.nzuguem.notify.models.Customer;

@Repository
public class InMemoryCustomers implements Customers {

    private final static Map<String, Customer> CUSTOMERS = Map.ofEntries(
        Map.entry("1", new Customer("1", "Nzuguem", "Kevin","kevin.nzuguem@example.com", "0686991712"))
    );

    @Override
    public Optional<Customer> get(String id) {

        return Optional.ofNullable(CUSTOMERS.get(id));
    }

}
