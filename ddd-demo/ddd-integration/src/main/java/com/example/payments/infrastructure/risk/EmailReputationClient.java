package com.example.payments.infrastructure.risk;

import java.util.UUID;

public interface EmailReputationClient {

    ReputationResult lookup(UUID customerId);

    record ReputationResult(boolean deliverable, int score, String provider) {
    }
}

