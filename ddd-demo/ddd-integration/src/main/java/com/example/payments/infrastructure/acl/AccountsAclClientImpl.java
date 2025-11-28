package com.example.payments.infrastructure.acl;

import com.example.payments.domain.model.payment.PaymentMethod;
import com.example.payments.domain.model.payment.PaymentMethodType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class AccountsAclClientImpl implements AccountsAclClient {

    private final WebClient webClient;

    public AccountsAclClientImpl(WebClient.Builder builder) {
        this.webClient = builder.clone().build();
    }

    @Override
    public PaymentMethod fetchPaymentMethod(UUID paymentMethodId) {
        var dto = webClient.get()
            .uri("http://accounts-service/internal/payment-methods/{id}", paymentMethodId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(PaymentMethodDto.class)
            .block();

        if (dto == null) {
            throw new IllegalStateException("Unable to load payment method");
        }

        return new PaymentMethod(
            dto.id(),
            dto.customerId(),
            dto.type(),
            dto.token(),
            dto.active(),
            dto.createdAt()
        );
    }

    private record PaymentMethodDto(UUID id,
                                    UUID customerId,
                                    PaymentMethodType type,
                                    String token,
                                    boolean active,
                                    OffsetDateTime createdAt) {
    }
}

