package me.nzuguem.notify.business;

import org.springframework.stereotype.Component;

import me.nzuguem.notify.exceptions.CustomerNotFoundException;
import me.nzuguem.notify.models.NotifyRequest;
import me.nzuguem.notify.models.SenderRequest;
import me.nzuguem.notify.services.dao.Customers;
import me.nzuguem.notify.services.notifications.SenderFactory;

@Component
public class NotifyBusiness {

    private final SenderFactory senderFactory;

    private final Customers customers;

    public NotifyBusiness(SenderFactory senderFactory, Customers customers) {
        this.senderFactory = senderFactory;
        this.customers = customers;
    }

    public void notify(NotifyRequest notifyRequest) {

        var customer = customers.get(notifyRequest.customerId())
            .orElseThrow(() -> new CustomerNotFoundException("Customer %s not found".formatted(notifyRequest.customerId())));

        var sender = senderFactory.getSender(notifyRequest.channel());

        var senderRequest = new SenderRequest(customer, 
            notifyRequest.notificationType(), 
            notifyRequest.channel(),
            notifyRequest.context());

        sender.send(senderRequest);
    }
    
}
