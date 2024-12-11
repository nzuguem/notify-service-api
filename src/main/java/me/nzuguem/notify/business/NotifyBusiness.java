package me.nzuguem.notify.business;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import me.nzuguem.notify.exceptions.CustomerNotFoundException;
import me.nzuguem.notify.models.NotifyRequest;
import me.nzuguem.notify.models.SenderRequest;
import me.nzuguem.notify.services.dao.Customers;
import me.nzuguem.notify.services.notifications.factory.SenderFactory;

@Component
public class NotifyBusiness {

    private final SenderFactory senderFactory;

    private final Customers customers;

    private final TransactionTemplate transactionTemplate;

    public NotifyBusiness(SenderFactory senderFactory, Customers customers, TransactionTemplate transactionTemplate) {
        this.senderFactory = senderFactory;
        this.customers = customers;
        this.transactionTemplate = transactionTemplate;
    }

    public void notify(NotifyRequest notifyRequest) {

        var sender = this.senderFactory.getSender(notifyRequest.channel(), notifyRequest.notificationType());

        // The transaction is released at the end of the method execution.
        // This means that any input/output operation after the database has been accessed will prolong the transaction, and therefore the connection to the database.
        // It is bad practice to depend on another external system in a transactional method
        // INFO  c.v.flexypool.FlexyPoolDataSource - Connection leased for 80 millis
        var customer = this.transactionTemplate.execute((__) -> {
            return customers.get(notifyRequest.customerId())
                        .orElseThrow(() -> new CustomerNotFoundException("Customer %s not found".formatted(notifyRequest.customerId())));
        });

        var senderRequest = new SenderRequest(customer,
            notifyRequest.notificationType(),
            notifyRequest.channel(),
            notifyRequest.context());

        sender.send(senderRequest);
    }

    @Transactional // c.v.flexypool.FlexyPoolDataSource - Connection leased for 104 millis
    public void notifyWithTransactionnalAnnotation(NotifyRequest notifyRequest) {

        var sender = this.senderFactory.getSender(notifyRequest.channel(), notifyRequest.notificationType());

        var customer = customers.get(notifyRequest.customerId())
                        .orElseThrow(() -> new CustomerNotFoundException("Customer %s not found".formatted(notifyRequest.customerId())));

        var senderRequest = new SenderRequest(customer,
            notifyRequest.notificationType(),
            notifyRequest.channel(),
            notifyRequest.context());

        sender.send(senderRequest);
    }

}
