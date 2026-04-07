package org.example.apiservice.client.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class KafkaMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topicName;

    public KafkaMessageProducer(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${appointments.topic-name}") String topicName
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }
    public void send(Object message, String key) {
        try {
            kafkaTemplate.send(topicName, key, message).get(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to send appointment event to Kafka topic '" + topicName + "'", ex);
        }
    }
}
