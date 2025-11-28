package com.example.payments.infrastructure.risk;

import java.util.UUID;

public interface PhoneReputationClient {

    PhoneReputation lookup(UUID customerId);

    record PhoneReputation(boolean active, String carrier, String countryCode) {
    }
}

