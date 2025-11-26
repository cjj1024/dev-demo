package com.example.payments.application.dto;

import com.example.payments.domain.model.payment.PaymentStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PaymentResponseDto(UUID paymentId,
                                 UUID customerId,
                                 BigDecimal amount,
                                 String currency,
                                 String description,
                                 PaymentStatus status,
                                 OffsetDateTime createdAt,
                                 OffsetDateTime updatedAt) {
}

