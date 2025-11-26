package com.example.payments.infrastructure.messaging;

import com.example.payments.domain.model.payment.PaymentDomainEvent;

public interface PaymentEventPublisher {

    void publish(PaymentDomainEvent event);
}

