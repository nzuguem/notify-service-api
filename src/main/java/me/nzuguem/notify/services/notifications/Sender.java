package me.nzuguem.notify.services.notifications;

import me.nzuguem.notify.models.SenderRequest;

public interface Sender {

    void send(SenderRequest senderRequest);
}
