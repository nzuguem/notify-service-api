package me.nzuguem.notify.services.notifications.sms;

import org.springframework.stereotype.Service;

import me.nzuguem.notify.models.SenderRequest;
import me.nzuguem.notify.services.notifications.Sender;

@Service
public class SmsSender implements Sender{

    @Override
    public void send(SenderRequest senderRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'send' for SMS");
    }

}
