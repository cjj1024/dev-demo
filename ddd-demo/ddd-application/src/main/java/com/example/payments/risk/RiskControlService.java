package com.example.payments.risk;

import com.example.payments.domain.model.payment.PaymentDomainEvent;
import com.example.payments.infrastructure.messaging.RiskDecisionPublisher;
import com.example.payments.infrastructure.risk.AddressVerificationClient;
import com.example.payments.infrastructure.risk.EmailReputationClient;
import com.example.payments.infrastructure.risk.PhoneReputationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RiskControlService {

    private static final Logger log = LoggerFactory.getLogger(RiskControlService.class);

    private final AddressVerificationClient addressClient;
    private final EmailReputationClient emailClient;
    private final PhoneReputationClient phoneClient;
    private final RiskDecisionPublisher riskDecisionPublisher;

    public RiskControlService(AddressVerificationClient addressClient,
                              EmailReputationClient emailClient,
                              PhoneReputationClient phoneClient,
                              RiskDecisionPublisher riskDecisionPublisher) {
        this.addressClient = addressClient;
        this.emailClient = emailClient;
        this.phoneClient = phoneClient;
        this.riskDecisionPublisher = riskDecisionPublisher;
    }

    public void evaluate(PaymentDomainEvent event) {
        if (!"PaymentCreated".equals(event.type())) {
            return;
        }

        var address = addressClient.verify(event.customerId());
        var email = emailClient.lookup(event.customerId());
        var phone = phoneClient.lookup(event.customerId());

        var score = score(address, email, phone, event.amount());
        String verdict = score >= 70 ? "APPROVED" : (score <= 40 ? "REJECTED" : "REVIEW");
        String reason = "Score=" + score;

        log.info("Risk decision for payment {} -> {} ({})", event.paymentId(), verdict, reason);
        riskDecisionPublisher.publish(event.paymentId(), verdict, reason);
    }

    private int score(AddressVerificationClient.AddressVerificationResult address,
                      EmailReputationClient.ReputationResult email,
                      PhoneReputationClient.PhoneReputation phone,
                      BigDecimal amount) {
        int base = 50;
        if (address.valid()) {
            base += 10;
        } else {
            base -= 15;
        }

        base += Math.min(email.score(), 20);
        if (phone.active()) {
            base += 10;
        } else {
            base -= 10;
        }

        if (amount != null && amount.doubleValue() > 1000) {
            base -= 10;
        }
        return Math.max(0, Math.min(100, base));
    }
}

