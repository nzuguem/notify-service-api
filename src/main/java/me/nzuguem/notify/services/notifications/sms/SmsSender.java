package me.nzuguem.notify.services.notifications.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import me.nzuguem.notify.models.SenderRequest;
import me.nzuguem.notify.services.notifications.Sender;
import me.nzuguem.notify.services.notifications.renderer.TemplateRenderer;

@Service
public class SmsSender implements Sender{

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSender.class);

    @Value("${notify.sms.from}")
    private String from;

    private final TemplateRenderer templateRenderer;

    public SmsSender(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    @Override
    public void send(SenderRequest senderRequest) {

        var text = this.templateRenderer.render(senderRequest.context(),
        "%s.%s.jte".formatted(senderRequest.type(), senderRequest.channel()));

        LOGGER.info("From: {}, To: {}, Text : {}", from, senderRequest.customer().phoneNumber(), text);
    }

}
