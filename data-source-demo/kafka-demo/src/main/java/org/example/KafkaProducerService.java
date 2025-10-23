package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String message) {
        CompletableFuture<Void> future = kafkaTemplate.send(topic, message)
                .completable()
                .thenAccept(result -> System.out.println("Sent: " + message + " to " + result.getRecordMetadata()))
                .exceptionally(ex -> {
                    System.err.println("Failed to send message: " + ex.getMessage());
                    return null;
                });
    }
}
