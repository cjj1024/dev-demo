package com.example.payments.infrastructure.messaging;

import com.example.payments.infrastructure.messaging.event.RiskDecisionMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaRiskDecisionPublisher implements RiskDecisionPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaRiskDecisionPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(UUID paymentId, String verdict, String reason) {
        var payload = new RiskDecisionMessage(paymentId, verdict, reason, java.time.OffsetDateTime.now());
        kafkaTemplate.send("risk.decisions", paymentId.toString(), payload);
    }
}

