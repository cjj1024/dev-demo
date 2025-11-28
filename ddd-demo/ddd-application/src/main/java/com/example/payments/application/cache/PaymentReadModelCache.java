package com.example.payments.application.cache;

import com.example.payments.application.dto.PaymentResponseDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
public class PaymentReadModelCache {

    private static final Duration TTL = Duration.ofMinutes(10);
    private final RedisTemplate<String, Object> redisTemplate;

    public PaymentReadModelCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void put(PaymentResponseDto dto) {
        redisTemplate.opsForValue().set(key(dto.paymentId()), dto, TTL);
    }

    public PaymentResponseDto get(UUID paymentId) {
        Object obj = redisTemplate.opsForValue().get(key(paymentId));
        if (obj instanceof PaymentResponseDto dto) {
            return dto;
        }
        return null;
    }

    private String key(UUID paymentId) {
        return "payment:summary:" + paymentId;
    }
}

