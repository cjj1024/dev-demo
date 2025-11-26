package com.example.payments.infrastructure.messaging.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RiskDecisionMessage(UUID paymentId, String verdict, String reason, OffsetDateTime decidedAt) {
}

