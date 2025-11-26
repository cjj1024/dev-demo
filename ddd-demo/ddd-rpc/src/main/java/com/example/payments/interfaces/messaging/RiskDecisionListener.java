package com.example.payments.interfaces.messaging;

import com.example.payments.application.service.PaymentApplicationService;
import com.example.payments.domain.model.payment.RiskDecision;
import com.example.payments.infrastructure.messaging.event.RiskDecisionMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RiskDecisionListener {

    private final PaymentApplicationService paymentApplicationService;

    public RiskDecisionListener(PaymentApplicationService paymentApplicationService) {
        this.paymentApplicationService = paymentApplicationService;
    }

    @KafkaListener(topics = "risk.decisions", groupId = "payments-service", containerFactory = "riskDecisionContainerFactory")
    public void listen(RiskDecisionMessage message) {
        var decision = switch (message.verdict()) {
            case "APPROVED" -> RiskDecision.approved();
            case "REJECTED" -> RiskDecision.rejected(message.reason());
            case "REVIEW" -> RiskDecision.review(message.reason());
            default -> RiskDecision.pending();
        };
        paymentApplicationService.handleRiskDecision(message.paymentId(), decision);
    }
}

