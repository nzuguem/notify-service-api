package me.nzuguem.notify.services.notifications.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import me.nzuguem.notify.models.SenderRequest;
import me.nzuguem.notify.services.notifications.Sender;
import me.nzuguem.notify.services.notifications.renderer.TemplateRenderer;

@Service
public class SmtpSender implements Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmtpSender.class);

    @Value("${notify.smtp.from}")
    private String from;

    private final JavaMailSender mailSender;

    private final TemplateRenderer templateRenderer;

    public SmtpSender(JavaMailSender mailSender,
                    TemplateRenderer templateRenderer) {
        this.mailSender = mailSender;
        this.templateRenderer = templateRenderer;
    }

    public void send(SenderRequest senderRequest) {

        try {
            var message = mailSender.createMimeMessage();

            message.setFrom(new InternetAddress(this.from));
            message.setRecipients(MimeMessage.RecipientType.TO, senderRequest.customer().email());

            var context = senderRequest.context();
            message.setSubject(senderRequest.type().getSubject(context));

            var htmlTemplate = this.templateRenderer.render(context,
                                "%s.%s.jte".formatted(senderRequest.type(), senderRequest.channel()));

            message.setContent(htmlTemplate, "text/html; charset=utf-8");

            LOGGER.info("Mail sending to customer {}", senderRequest.customer().id());
            this.mailSender.send(message);

        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }

    }

}
