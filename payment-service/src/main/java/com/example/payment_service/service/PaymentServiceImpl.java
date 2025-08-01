package com.example.payment_service.service;

import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.kafka.PaymentListener;
import com.example.payment_service.repository.PaymentRepository;
import com.example.payment_service.service.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    PaymentListener paymentListener;
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public PaymentRequest createPayment(PaymentRequest request) {
        Payment payment = paymentMapper.toEntity(request);
        paymentRepository.save(payment);
        paymentListener.sendPaymentResult(request);
        return request;
    }
}
