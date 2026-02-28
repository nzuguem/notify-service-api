package me.nzuguem.notify.configurations.events;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ShareKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultShareConsumerFactory;
import org.springframework.kafka.core.ShareConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

@Configuration
public class ShareConsumerConfiguration {

    @Bean
    public ShareConsumerFactory<String, String> shareConsumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        var consumer = kafkaProperties.getConsumer();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumer.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumer.getValueDeserializer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.getGroupId());
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, consumer.getProperties().get(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG));
        props.put(ConsumerConfig.SHARE_ACKNOWLEDGEMENT_MODE_CONFIG, "explicit");
        props.put(JacksonJsonDeserializer.TRUSTED_PACKAGES,
            consumer.getProperties().get(JacksonJsonDeserializer.TRUSTED_PACKAGES));
        props.put(JacksonJsonDeserializer.USE_TYPE_INFO_HEADERS,
            consumer.getProperties().get(JacksonJsonDeserializer.USE_TYPE_INFO_HEADERS));
        props.put(JacksonJsonDeserializer.TYPE_MAPPINGS,
            consumer.getProperties().get(JacksonJsonDeserializer.TYPE_MAPPINGS));
        return new DefaultShareConsumerFactory<>(props);
    }

    @Bean
    public ShareKafkaListenerContainerFactory<String, String>
            shareKafkaListenerContainerFactory(
                ShareConsumerFactory<String, String> shareConsumerFactory) {
        return new ShareKafkaListenerContainerFactory<>(shareConsumerFactory);
    }

    @Bean
    public NewTopic notifications(@Value("${app.kafka.topics.notifications}") String topicName) {
        return TopicBuilder.name(topicName)
                .partitions(2)
                .replicas(1)
                .build();
    }
}
