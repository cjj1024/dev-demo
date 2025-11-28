package com.example.payments.domain.model.payment;

import java.util.UUID;

public record PaymentId(UUID value) {

    public static PaymentId newId() {
        return new PaymentId(UUID.randomUUID());
    }
}

