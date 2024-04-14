package com.project.ecommerce.checkout.service;

import com.project.ecommerce.checkout.dto.request.CheckoutRequest;
import com.project.ecommerce.checkout.dto.response.CheckoutResponse;
import com.project.ecommerce.utils.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICheckoutService {

    CheckoutResponse createCheckout (CheckoutRequest request) throws ApiException;
    List<CheckoutResponse> getAllCheckout() throws ApiException;
    CheckoutResponse getByNumberInvoice (String numberInvoice) throws ApiException;
    CheckoutResponse updateCheckout(long idInvoice, CheckoutRequest request) throws ApiException;
    void deleteCheckout(String numberInvoice) throws ApiException;

}
