package com.project.ecommerce.payments.controller;

import com.project.ecommerce.payments.dto.request.PaymentRequest;
import com.project.ecommerce.payments.dto.response.PaymentResponse;
import com.project.ecommerce.payments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    private List<PaymentResponse> getAll() {
        return paymentService.getAll();
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    private PaymentResponse getById(@PathVariable long id) {
        return paymentService.getById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    private void create(@RequestBody PaymentRequest paymentRequest) {
        paymentService.create(paymentRequest);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void update(@RequestBody PaymentRequest paymentRequest,
                        @PathVariable long id) {
        paymentService.update(paymentRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void delete(@PathVariable int id) {
        paymentService.delete(id);
    }
}
