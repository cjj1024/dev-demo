package com.example.payments.infrastructure.messaging;

import com.example.payments.domain.model.payment.PaymentDomainEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPaymentEventPublisher implements PaymentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaPaymentEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(PaymentDomainEvent event) {
        kafkaTemplate.send("payments.lifecycle", event.paymentId().toString(), event);
    }
}

