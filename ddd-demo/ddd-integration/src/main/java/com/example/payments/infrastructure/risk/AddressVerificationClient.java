package com.example.payments.infrastructure.risk;

import java.util.UUID;

public interface AddressVerificationClient {

    AddressVerificationResult verify(UUID customerId);

    record AddressVerificationResult(boolean valid, String countryCode, String riskFlag) {
    }
}

