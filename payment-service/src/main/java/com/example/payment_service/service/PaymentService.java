package com.example.payment_service.service;

import com.example.payment_service.dto.PaymentRequest;

public interface PaymentService {
    PaymentRequest createPayment(PaymentRequest request);
}
