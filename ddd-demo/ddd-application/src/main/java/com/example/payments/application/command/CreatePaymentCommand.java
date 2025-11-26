package com.example.payments.application.command;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentCommand(UUID customerId,
                                   UUID paymentMethodId,
                                   BigDecimal amount,
                                   String currency,
                                   String description) {
}

