package me.nzuguem.notify.services.notifications;

import org.springframework.stereotype.Component;

import me.nzuguem.notify.models.Channel;
import me.nzuguem.notify.services.notifications.sms.SmsSender;
import me.nzuguem.notify.services.notifications.smtp.SmtpSender;

@Component
public class SenderFactory {

    private final SmsSender smsSender;

    private final SmtpSender smtpSender;

    public SenderFactory(SmsSender smsSender, SmtpSender smtpSender) {
        this.smsSender = smsSender;
        this.smtpSender = smtpSender;
    }

    public Sender getSender(Channel channel) {
        
        return switch (channel) {
            case SMS -> smsSender;
            case SMTP -> smtpSender;
        };
    }
    
}
