package com.example.payments.infrastructure.persistence;

import com.example.payments.domain.model.payment.Payment;
import com.example.payments.domain.model.payment.PaymentId;
import com.example.payments.domain.model.payment.PaymentRepository;
import com.example.payments.infrastructure.persistence.mybatis.PaymentMapper;
import com.example.payments.infrastructure.persistence.mybatis.PaymentRecord;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentMapper paymentMapper;

    public PaymentRepositoryImpl(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Payment save(Payment payment) {
        var record = PaymentRecord.fromDomain(payment);
        paymentMapper.upsert(record);
        return payment;
    }

    @Override
    public Optional<Payment> findById(PaymentId paymentId) {
        return Optional.ofNullable(paymentMapper.selectById(paymentId.value()))
            .map(PaymentRecord::toDomain);
    }

    @Override
    public Optional<Payment> findById(UUID rawId) {
        return Optional.ofNullable(paymentMapper.selectById(rawId))
            .map(PaymentRecord::toDomain);
    }
}

