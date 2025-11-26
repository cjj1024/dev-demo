package com.example.payments.domain.model.payment;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Accessors(fluent = true)
public class PaymentMethod {

    private final UUID methodId;
    private final UUID customerId;
    private final PaymentMethodType type;
    private final String token;
    private final boolean active;
    private final OffsetDateTime createdAt;

    public PaymentMethod(UUID methodId,
                         UUID customerId,
                         PaymentMethodType type,
                         String token,
                         boolean active,
                         OffsetDateTime createdAt) {
        this.methodId = Objects.requireNonNull(methodId);
        this.customerId = Objects.requireNonNull(customerId);
        this.type = Objects.requireNonNull(type);
        this.token = Objects.requireNonNull(token);
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt);
    }
}

