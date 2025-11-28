package com.example.payments.domain.model.payment;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.Objects;

@Getter
@Accessors(fluent = true)
public class RiskDecision {

    public enum Verdict {
        APPROVED, REJECTED, REVIEW, PENDING
        }

    private final Verdict verdict;
    private final String reason;
    private final OffsetDateTime decidedAt;

    private RiskDecision(Verdict verdict, String reason, OffsetDateTime decidedAt) {
        this.verdict = Objects.requireNonNull(verdict);
        this.reason = reason;
        this.decidedAt = decidedAt;
    }

    public static RiskDecision pending() {
        return new RiskDecision(Verdict.PENDING, null, OffsetDateTime.now());
    }

    public static RiskDecision approved() {
        return new RiskDecision(Verdict.APPROVED, null, OffsetDateTime.now());
    }

    public static RiskDecision rejected(String reason) {
        return new RiskDecision(Verdict.REJECTED, reason, OffsetDateTime.now());
    }

    public static RiskDecision review(String reason) {
        return new RiskDecision(Verdict.REVIEW, reason, OffsetDateTime.now());
    }

    public static RiskDecision restore(String verdict, String reason, OffsetDateTime decidedAt) {
        if (verdict == null) {
            return pending();
        }
        return new RiskDecision(Verdict.valueOf(verdict), reason, decidedAt == null ? OffsetDateTime.now() : decidedAt);
    }

    public boolean isApproved() {
        return verdict == Verdict.APPROVED;
    }

    public boolean isRejected() {
        return verdict == Verdict.REJECTED;
    }

}

