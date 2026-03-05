package me.nzuguem.notify.configurations.health;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.health.contributor.AbstractHealthIndicator;
import org.springframework.boot.health.contributor.Health;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

@Component
public class KafkaHealthIndicator extends AbstractHealthIndicator {

    @Value("${app.kafka.topics.notifications}")
    private String topicName;

    private final KafkaAdmin kafkaAdmin;

    public KafkaHealthIndicator(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    @Override
    protected void doHealthCheck(Health.@NonNull Builder builder) {
        try {
            this.kafkaAdmin.describeTopics(this.topicName);
            builder.up();
        } catch (Exception e) {
            builder.down(e);
        }
    }
}
