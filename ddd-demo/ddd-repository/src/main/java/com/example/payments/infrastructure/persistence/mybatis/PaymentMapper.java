package com.example.payments.infrastructure.persistence.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface PaymentMapper {

    int upsert(PaymentRecord record);

    PaymentRecord selectById(@Param("id") UUID id);
}

