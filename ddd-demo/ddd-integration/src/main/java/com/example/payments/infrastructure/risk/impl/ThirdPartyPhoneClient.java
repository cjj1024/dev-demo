package com.example.payments.infrastructure.risk.impl;

import com.example.payments.infrastructure.risk.PhoneReputationClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class ThirdPartyPhoneClient implements PhoneReputationClient {

    private final WebClient webClient;

    public ThirdPartyPhoneClient(WebClient.Builder builder) {
        this.webClient = builder.clone().baseUrl("http://phone-intel").build();
    }

    @Override
    public PhoneReputation lookup(UUID customerId) {
        var result = webClient.get()
            .uri("/lookup/{customerId}", customerId)
            .retrieve()
            .bodyToMono(PhoneReputation.class)
            .onErrorReturn(new PhoneReputation(false, "unknown", "XX"))
            .block();
        return result == null ? new PhoneReputation(false, "unknown", "XX") : result;
    }
}

