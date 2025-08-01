package com.example.payment_service.service.mapper;

import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentRequest toDTO(Payment payment);

    Payment toEntity(PaymentRequest paymentRequest);

}
