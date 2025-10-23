package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "demo-topic", groupId = "demo-group")
    public void listen(Acknowledgment ack, ConsumerRecord<String, String> record) {
        try {
            System.out.println("Received: " + record.value());
            ack.acknowledge();
        } catch (Exception e) {
            System.err.println("process failed: " + record.value() + " - " + e.getMessage());
        }
    }
}
