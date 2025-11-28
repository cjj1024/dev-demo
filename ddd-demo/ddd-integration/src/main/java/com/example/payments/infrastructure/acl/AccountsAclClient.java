package com.example.payments.infrastructure.acl;

import com.example.payments.domain.model.payment.PaymentMethod;

import java.util.UUID;

public interface AccountsAclClient {

    PaymentMethod fetchPaymentMethod(UUID paymentMethodId);
}

