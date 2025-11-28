package com.example.payments.infrastructure.persistence.mybatis;

import com.example.payments.domain.model.payment.Payment;
import com.example.payments.domain.model.payment.PaymentMethod;
import com.example.payments.domain.model.payment.PaymentMethodType;
import com.example.payments.domain.model.payment.PaymentStatus;
import com.example.payments.domain.model.payment.RiskDecision;
import com.example.payments.domain.model.shared.Money;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PaymentRecord {

    private UUID id;
    private UUID customerId;
    private UUID paymentMethodId;
    private PaymentMethodType paymentMethodType;
    private String paymentMethodToken;
    private BigDecimal amount;
    private String currency;
    private String description;
    private PaymentStatus status;
    private String riskVerdict;
    private String riskReason;
    private OffsetDateTime riskDecidedAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static PaymentRecord fromDomain(Payment payment) {
        var record = new PaymentRecord();
        record.id = payment.paymentId().value();
        record.customerId = payment.method().customerId();
        record.paymentMethodId = payment.method().methodId();
        record.paymentMethodType = payment.method().type();
        record.paymentMethodToken = payment.method().token();
        record.amount = payment.amount().amount();
        record.currency = payment.amount().currency();
        record.description = payment.description();
        record.status = payment.status();
        record.riskVerdict = payment.riskDecision().verdict().name();
        record.riskReason = payment.riskDecision().reason();
        record.riskDecidedAt = payment.riskDecision().decidedAt();
        record.createdAt = payment.createdAt();
        record.updatedAt = payment.updatedAt();
        return record;
    }

    public Payment toDomain() {
        PaymentMethod method = new PaymentMethod(
            paymentMethodId,
            customerId,
            paymentMethodType,
            paymentMethodToken,
            true,
            createdAt
        );
        return Payment.restore(
            new com.example.payments.domain.model.payment.PaymentId(id),
            new Money(amount, currency),
            description,
            method,
            status,
            RiskDecision.restore(riskVerdict, riskReason, riskDecidedAt),
            createdAt,
            updatedAt
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(UUID paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public String getPaymentMethodToken() {
        return paymentMethodToken;
    }

    public void setPaymentMethodToken(String paymentMethodToken) {
        this.paymentMethodToken = paymentMethodToken;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getRiskVerdict() {
        return riskVerdict;
    }

    public void setRiskVerdict(String riskVerdict) {
        this.riskVerdict = riskVerdict;
    }

    public String getRiskReason() {
        return riskReason;
    }

    public void setRiskReason(String riskReason) {
        this.riskReason = riskReason;
    }

    public OffsetDateTime getRiskDecidedAt() {
        return riskDecidedAt;
    }

    public void setRiskDecidedAt(OffsetDateTime riskDecidedAt) {
        this.riskDecidedAt = riskDecidedAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

