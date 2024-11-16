package me.nzuguem.notify.services.dao;

import java.util.Optional;

import me.nzuguem.notify.models.Customer;

public interface Customers {

    Optional<Customer> get(String id);

}
