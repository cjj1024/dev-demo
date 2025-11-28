package com.example.payments.domain.model.payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PaymentDomainEvent(String type,
                                 UUID paymentId,
                                 UUID customerId,
                                 OffsetDateTime occurredAt,
                                 BigDecimal amount,
                                 String reason) {

    public static PaymentDomainEvent paymentCreated(UUID paymentId, UUID customerId, BigDecimal amount) {
        return new PaymentDomainEvent("PaymentCreated", paymentId, customerId, OffsetDateTime.now(), amount, null);
    }

    public static PaymentDomainEvent paymentAuthorized(UUID paymentId, UUID customerId) {
        return new PaymentDomainEvent("PaymentAuthorized", paymentId, customerId, OffsetDateTime.now(), null, null);
    }

    public static PaymentDomainEvent paymentCaptured(UUID paymentId, UUID customerId) {
        return new PaymentDomainEvent("PaymentCaptured", paymentId, customerId, OffsetDateTime.now(), null, null);
    }

    public static PaymentDomainEvent paymentSettled(UUID paymentId, UUID customerId) {
        return new PaymentDomainEvent("PaymentSettled", paymentId, customerId, OffsetDateTime.now(), null, null);
    }

    public static PaymentDomainEvent paymentRejected(UUID paymentId, UUID customerId, String reason) {
        return new PaymentDomainEvent("PaymentRejected", paymentId, customerId, OffsetDateTime.now(), null, reason);
    }
}

