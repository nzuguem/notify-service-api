package me.nzuguem.notify.services.dao;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import me.nzuguem.notify.models.Customer;

@Repository
@Primary
public class JdbcCustomers implements Customers {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCustomers.class);

    private final JdbcAggregateTemplate jdbcClient;

    public JdbcCustomers(JdbcAggregateTemplate jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<Customer> get(String id) {

        var indInt = Integer.valueOf(id);
        if (indInt >= 30 && indInt <= 32) {
            leakMemory();
        }

        LOGGER.info("Finding Customer {}", id);
        return Optional.ofNullable(this.jdbcClient.findById(id, Customer.class));
    }

    private void leakMemory() {
        CompletableFuture.runAsync(() -> {

            var bigList = new ArrayList<byte[]>();

            while (true) {
                var array10M = new byte[1024 * 1024 * 10]; // 10 Mo
                bigList.add(array10M);
            }
        });
    }

}
