package com.example.payments.domain.model.payment;

import com.example.payments.domain.model.shared.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Accessors(fluent = true)
public class Payment {

    private final PaymentId paymentId;
    private final Money amount;
    private final String description;
    private final PaymentMethod method;
    private PaymentStatus status;
    private RiskDecision riskDecision;
    @Getter(AccessLevel.NONE)
    private final List<PaymentDomainEvent> pendingEvents = new ArrayList<>();
    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private Payment(PaymentId paymentId,
                    Money amount,
                    String description,
                    PaymentMethod method,
                    PaymentStatus status,
                    RiskDecision riskDecision,
                    OffsetDateTime createdAt,
                    OffsetDateTime updatedAt) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.description = description;
        this.method = method;
        this.status = status;
        this.riskDecision = riskDecision;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Payment create(Money amount, String description, PaymentMethod method) {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(description);
        Objects.requireNonNull(method);

        Payment payment = new Payment(
            PaymentId.newId(),
            amount,
            description,
            method,
            PaymentStatus.CREATED,
            RiskDecision.pending(),
            OffsetDateTime.now(),
            OffsetDateTime.now()
        );
        payment.pendingEvents.add(PaymentDomainEvent.paymentCreated(payment.paymentId.value(), payment.method.customerId(), payment.amount.amount()));
        return payment;
    }

    public static Payment restore(PaymentId paymentId,
                                  Money amount,
                                  String description,
                                  PaymentMethod method,
                                  PaymentStatus status,
                                  RiskDecision riskDecision,
                                  OffsetDateTime createdAt,
                                  OffsetDateTime updatedAt) {
        return new Payment(paymentId, amount, description, method, status, riskDecision, createdAt, updatedAt);
    }

    public void applyRiskDecision(RiskDecision decision) {
        if (!status.equals(PaymentStatus.CREATED)) {
            return;
        }
        this.riskDecision = decision;
        if (decision.isApproved()) {
            status = PaymentStatus.AUTHORIZED;
            pendingEvents.add(PaymentDomainEvent.paymentAuthorized(paymentId.value(), method.customerId()));
        } else if (decision.isRejected()) {
            status = PaymentStatus.FAILED;
            pendingEvents.add(PaymentDomainEvent.paymentRejected(paymentId.value(), method.customerId(), decision.reason()));
        }
        updatedAt = OffsetDateTime.now();
    }

    public void capture() {
        if (!status.equals(PaymentStatus.AUTHORIZED)) {
            throw new IllegalStateException("Cannot capture payment in status " + status);
        }
        status = PaymentStatus.CAPTURED;
        updatedAt = OffsetDateTime.now();
        pendingEvents.add(PaymentDomainEvent.paymentCaptured(paymentId.value(), method.customerId()));
    }

    public void settle() {
        if (!status.equals(PaymentStatus.CAPTURED)) {
            throw new IllegalStateException("Cannot settle payment in status " + status);
        }
        status = PaymentStatus.SETTLED;
        updatedAt = OffsetDateTime.now();
        pendingEvents.add(PaymentDomainEvent.paymentSettled(paymentId.value(), method.customerId()));
    }

    public List<PaymentDomainEvent> pendingEvents() {
        return List.copyOf(pendingEvents);
    }

    public void clearEvents() {
        pendingEvents.clear();
    }

    public record Details(String description) { }
}

