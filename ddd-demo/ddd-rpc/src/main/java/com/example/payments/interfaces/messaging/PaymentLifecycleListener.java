package com.example.payments.interfaces.messaging;

import com.example.payments.domain.model.payment.PaymentDomainEvent;
import com.example.payments.risk.RiskControlService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentLifecycleListener {

    private final RiskControlService riskControlService;

    public PaymentLifecycleListener(RiskControlService riskControlService) {
        this.riskControlService = riskControlService;
    }

    @KafkaListener(topics = "payments.lifecycle", groupId = "risk-service", containerFactory = "paymentEventContainerFactory")
    public void listen(PaymentDomainEvent event) {
        riskControlService.evaluate(event);
    }
}

