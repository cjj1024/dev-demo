package com.example.payments.infrastructure.risk.impl;

import com.example.payments.infrastructure.risk.EmailReputationClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class ThirdPartyEmailClient implements EmailReputationClient {

    private final WebClient webClient;

    public ThirdPartyEmailClient(WebClient.Builder builder) {
        this.webClient = builder.clone().baseUrl("http://email-intel").build();
    }

    @Override
    public ReputationResult lookup(UUID customerId) {
        var result = webClient.get()
            .uri("/lookup/{customerId}", customerId)
            .retrieve()
            .bodyToMono(ReputationResult.class)
            .onErrorReturn(new ReputationResult(false, 0, "unknown"))
            .block();
        return result == null ? new ReputationResult(false, 0, "unknown") : result;
    }
}

