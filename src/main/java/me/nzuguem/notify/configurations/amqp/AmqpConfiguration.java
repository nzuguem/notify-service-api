package me.nzuguem.notify.configurations.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {

    public static final String NOTIFY_EXCAHNGE = "notify-exchange";
    public static final String NOTIFY_QUEUE_SMS = "notify-queue-sms";
    public static final String NOTIFY_QUEUE_EMAIL = "notify-queue-email";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(NOTIFY_EXCAHNGE);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(NOTIFY_QUEUE_SMS);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(NOTIFY_QUEUE_EMAIL);
    }


    @Bean
    public Binding smsBinding(Queue smsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(smsQueue).to(exchange).with("sms");
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, DirectExchange exchange) {
        return BindingBuilder.bind(emailQueue).to(exchange).with("email");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
