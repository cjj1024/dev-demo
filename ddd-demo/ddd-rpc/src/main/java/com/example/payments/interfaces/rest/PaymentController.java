package com.example.payments.interfaces.rest;

import com.example.payments.application.command.CreatePaymentCommand;
import com.example.payments.application.dto.PaymentResponseDto;
import com.example.payments.application.service.PaymentApplicationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;

    public PaymentController(PaymentApplicationService paymentApplicationService) {
        this.paymentApplicationService = paymentApplicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDto create(@Valid @RequestBody CreatePaymentRequest request) {
        var command = new CreatePaymentCommand(
            request.customerId(),
            request.paymentMethodId(),
            request.amount(),
            request.currency(),
            request.description()
        );
        return paymentApplicationService.createPayment(command);
    }

    @GetMapping("/{paymentId}")
    public PaymentResponseDto get(@PathVariable UUID paymentId) {
        return paymentApplicationService.getPayment(paymentId);
    }

    public record CreatePaymentRequest(@NotNull UUID customerId,
                                       @NotNull UUID paymentMethodId,
                                       @NotNull @DecimalMin("0.01") BigDecimal amount,
                                       @NotBlank @Size(min = 3, max = 3) String currency,
                                       @NotBlank @Size(max = 255) String description) {
    }
}

