package com.example.payments.infrastructure.risk.impl;

import com.example.payments.infrastructure.risk.AddressVerificationClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class ThirdPartyAddressClient implements AddressVerificationClient {

    private final WebClient webClient;

    public ThirdPartyAddressClient(WebClient.Builder builder) {
        this.webClient = builder.clone().baseUrl("http://address-intel").build();
    }

    @Override
    public AddressVerificationResult verify(UUID customerId) {
        var result = webClient.get()
            .uri("/lookup/{customerId}", customerId)
            .retrieve()
            .bodyToMono(AddressVerificationResult.class)
            .onErrorReturn(new AddressVerificationResult(false, "UNKNOWN", "UPSTREAM_ERROR"))
            .block();
        return result == null ? new AddressVerificationResult(false, "UNKNOWN", "NO_DATA") : result;
    }
}

