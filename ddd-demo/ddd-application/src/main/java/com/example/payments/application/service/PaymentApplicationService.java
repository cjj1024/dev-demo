package com.example.payments.application.service;

import com.example.payments.application.command.CreatePaymentCommand;
import com.example.payments.application.dto.PaymentResponseDto;
import com.example.payments.application.cache.PaymentReadModelCache;
import com.example.payments.domain.model.payment.Payment;
import com.example.payments.domain.model.payment.PaymentRepository;
import com.example.payments.domain.model.payment.PaymentStatus;
import com.example.payments.domain.model.payment.RiskDecision;
import com.example.payments.domain.model.shared.Money;
import com.example.payments.infrastructure.acl.AccountsAclClient;
import com.example.payments.infrastructure.messaging.PaymentEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentApplicationService {

    private final PaymentRepository paymentRepository;
    private final AccountsAclClient accountsAclClient;
    private final PaymentEventPublisher eventPublisher;
    private final PaymentReadModelCache readModelCache;

    public PaymentApplicationService(PaymentRepository paymentRepository,
                                     AccountsAclClient accountsAclClient,
                                     PaymentEventPublisher eventPublisher,
                                     PaymentReadModelCache readModelCache) {
        this.paymentRepository = paymentRepository;
        this.accountsAclClient = accountsAclClient;
        this.eventPublisher = eventPublisher;
        this.readModelCache = readModelCache;
    }

    @Transactional
    public PaymentResponseDto createPayment(CreatePaymentCommand command) {
        var paymentMethod = accountsAclClient.fetchPaymentMethod(command.paymentMethodId());
        var payment = Payment.create(
            new Money(command.amount(), command.currency()),
            command.description(),
            paymentMethod
        );
        var saved = paymentRepository.save(payment);
        publishEventsAndCache(saved);
        return toResponse(saved, paymentMethod.customerId());
    }

    @Transactional(readOnly = true)
    public PaymentResponseDto getPayment(UUID paymentId) {
        var cached = readModelCache.get(paymentId);
        if (cached != null) {
            return cached;
        }
        var payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        var response = toResponse(payment, payment.method().customerId());
        readModelCache.put(response);
        return response;
    }

    @Transactional
    public void handleRiskDecision(UUID paymentId, RiskDecision decision) {
        var payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new IllegalArgumentException("Payment not found for risk decision"));
        payment.applyRiskDecision(decision);
        paymentRepository.save(payment);
        publishEventsAndCache(payment);
    }

    private PaymentResponseDto toResponse(Payment payment, UUID customerId) {
        return new PaymentResponseDto(
            payment.paymentId().value(),
            customerId,
            payment.amount().amount(),
            payment.amount().currency(),
            payment.description(),
            payment.status(),
            payment.createdAt(),
            payment.updatedAt()
        );
    }

    private void publishEventsAndCache(Payment payment) {
        payment.pendingEvents().forEach(eventPublisher::publish);
        if (payment.status() == PaymentStatus.SETTLED || payment.status() == PaymentStatus.AUTHORIZED) {
            readModelCache.put(toResponse(payment, payment.method().customerId()));
        }
        payment.clearEvents();
    }
}

