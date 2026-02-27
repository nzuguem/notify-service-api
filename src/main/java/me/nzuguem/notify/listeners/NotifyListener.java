package me.nzuguem.notify.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.ShareAcknowledgment;
import org.springframework.stereotype.Component;

import me.nzuguem.notify.business.NotifyBusiness;
import me.nzuguem.notify.configurations.events.AmqpConfiguration;
import me.nzuguem.notify.models.NotifyRequest;
import tools.jackson.databind.ObjectMapper;

@Component
public class NotifyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyListener.class);

    private final NotifyBusiness notifyBusiness;
    private final ObjectMapper objectMapper;

    public NotifyListener(NotifyBusiness notifyBusiness, ObjectMapper objectMapper) {
        this.notifyBusiness = notifyBusiness;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {AmqpConfiguration.NOTIFY_QUEUE_SMS, AmqpConfiguration.NOTIFY_QUEUE_EMAIL})
    public void receiveMessageAmqp(final NotifyRequest notifyRequest) {

        try {
            this.notifyBusiness.notify(notifyRequest);
        }
        // Do not apply the AMQP RetryPolicy to certain exceptions
        catch (UnsupportedOperationException exception) {
            LOGGER.error(exception.getMessage(), exception);
        }
    }

    @KafkaListener(
        topics = "${app.kafka.topics.notifications}",
        containerFactory = "shareKafkaListenerContainerFactory"
    )
    public void receiveMessageKafkaQueue(final String notifyRequest, ShareAcknowledgment acknowledgment) {
        try {
            this.notifyBusiness.notify(this.objectMapper.readValue(notifyRequest, NotifyRequest.class));
            acknowledgment.acknowledge();
        }
        catch (UnsupportedOperationException exception) {
            LOGGER.error(exception.getMessage(), exception);
            acknowledgment.reject();
        }
    }

}

