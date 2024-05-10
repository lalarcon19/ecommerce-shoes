package com.project.ecommerce.checkout.service;

import com.project.ecommerce.checkout.dto.request.CheckoutRequest;
import com.project.ecommerce.checkout.dto.response.CheckoutResponse;
import com.project.ecommerce.utils.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICheckoutService {

    void createCheckout(CheckoutRequest request);

    List<CheckoutResponse> getAllCheckout();

    CheckoutResponse getByNumberInvoice(String numberInvoice);

    void deleteCheckout(String numberInvoice);

}
