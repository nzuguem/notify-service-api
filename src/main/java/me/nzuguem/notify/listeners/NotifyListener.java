package me.nzuguem.notify.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import me.nzuguem.notify.business.NotifyBusiness;
import me.nzuguem.notify.configurations.amqp.AmqpConfiguration;
import me.nzuguem.notify.models.NotifyRequest;

@Component
public class NotifyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyListener.class);

    private final NotifyBusiness notifyBusiness;

    public NotifyListener(NotifyBusiness notifyBusiness) {
        this.notifyBusiness = notifyBusiness;
    }

    @RabbitListener(queues = {AmqpConfiguration.NOTIFY_QUEUE_SMS, AmqpConfiguration.NOTIFY_QUEUE_EMAIL})
    public void receiveMessage(final NotifyRequest notifyRequest) {

        try {
            this.notifyBusiness.notify(notifyRequest);
        }
        // Do not apply the AMQP RetryPolicy to certain exceptions
        catch (UnsupportedOperationException exception) {
            LOGGER.error(exception.getMessage(), exception);
        }
    }

}

