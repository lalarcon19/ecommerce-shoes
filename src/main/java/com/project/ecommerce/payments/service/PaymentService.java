package com.project.ecommerce.payments.service;

import com.project.ecommerce.payments.dto.request.PaymentRequest;
import com.project.ecommerce.payments.dto.response.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    void create(PaymentRequest paymentRequest);

    PaymentResponse getById(long id);

    List<PaymentResponse> getAll();

    void update(PaymentRequest paymentRequest, long id);

    void delete(long id);
}
