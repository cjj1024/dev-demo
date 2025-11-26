package com.example.payments.infrastructure.messaging;

import java.util.UUID;

public interface RiskDecisionPublisher {

    void publish(UUID paymentId, String verdict, String reason);
}

