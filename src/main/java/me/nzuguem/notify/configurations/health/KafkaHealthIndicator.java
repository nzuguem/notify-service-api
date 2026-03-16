package me.nzuguem.notify.configurations.health;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.health.contributor.AbstractHealthIndicator;
import org.springframework.boot.health.contributor.Health;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

@Component
public class KafkaHealthIndicator extends AbstractHealthIndicator {

    private final String topicName;

    private final KafkaAdmin kafkaAdmin;

    public KafkaHealthIndicator(
        @Nullable KafkaAdmin kafkaAdmin,
        @Value("${app.kafka.topics.notifications}")  String topicName
    ) {
        this.kafkaAdmin = kafkaAdmin;
        this.topicName = topicName;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            this.kafkaAdmin.describeTopics(this.topicName);
            builder.up();
        } catch (Exception e) {
            builder.down(e);
        }
    }
}
