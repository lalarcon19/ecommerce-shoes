package com.project.ecommerce.payments.service.impl;

import com.project.ecommerce.payments.dto.request.PaymentRequest;
import com.project.ecommerce.payments.dto.response.PaymentResponse;
import com.project.ecommerce.payments.entity.Payment;
import com.project.ecommerce.payments.repository.PaymentRepository;
import com.project.ecommerce.payments.service.PaymentService;
import com.project.ecommerce.user.entity.UserEntity;
import com.project.ecommerce.user.respository.UserRepository;
import com.project.ecommerce.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public void create(PaymentRequest paymentRequest) {
        log.info("---- Creando metodo de pago. ----");
        UserEntity user = userRepository.findById(paymentRequest.getUserId());

        if (user == null) {
            log.error("El usuario no existe.");
            throw new ApiException("El usuario no existe.", HttpStatus.BAD_REQUEST);
        }

        Payment payment = requestToPayment(paymentRequest);
        payment.setUser(user);

        paymentRepository.saveAndFlush(payment);
    }

    @Override
    public PaymentResponse getById(long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isEmpty()) {
            log.error("No existe el metodo de pago.");
            throw new ApiException("No existe el metodo de pago", HttpStatus.BAD_REQUEST);
        }
        log.info("---- Se obtiene el metodo de pago. ----");
        return paymentToResponse(payment.get());
    }

    @Override
    public List<PaymentResponse> getAll() {
        List<Payment> payment = paymentRepository.findAll();
        if (payment.isEmpty()) {
            log.info("No hay metodos de pago.");
            return Collections.emptyList();
        }
        return payment.stream()
                .map(PaymentImpl::paymentToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void update(PaymentRequest paymentRequest, long id) {

    }

    @Override
    public void delete(long id) {

    }

    public Payment requestToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .paymentMethod(paymentRequest.getPaymentMethod())
                .franchises(paymentRequest.getFranchises())
                .cardNumber(addAsteristk(paymentRequest.getCardNumber()))
                .build();
    }

    public static PaymentResponse paymentToResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod())
                .franchises(payment.getFranchises())
                .cardNumber(payment.getCardNumber())
                .build();
    }

    public static String addAsteristk(String input) {
        if (input == null ) {
            log.info("El numero debe ser mayor a 16 caracteres.");
            throw new ApiException("El numero debe ser mayor a 16 caracteres.", HttpStatus.BAD_REQUEST);
        }

        int stringLength = input.length();
        int longitud = stringLength - 4;

        return String.format("%s%s%s", input.substring(0, 2),
                String.join("", Collections.nCopies(longitud, "*")),
                input.substring(stringLength - 2, stringLength));
    }
}
