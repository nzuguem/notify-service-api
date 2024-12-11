package me.nzuguem.notify.services.notifications.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import me.nzuguem.notify.models.SenderRequest;
import me.nzuguem.notify.services.notifications.Sender;

@Service
public class SmsSender implements Sender{

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSender.class);


    @Override
    public void send(SenderRequest senderRequest) {
        LOGGER.info("{}", senderRequest);
    }

}
