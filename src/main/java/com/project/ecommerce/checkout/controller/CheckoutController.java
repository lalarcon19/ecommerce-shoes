package com.project.ecommerce.checkout.controller;

import com.project.ecommerce.checkout.dto.request.CheckoutRequest;
import com.project.ecommerce.checkout.dto.response.CheckoutResponse;
import com.project.ecommerce.checkout.service.ICheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkout")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CheckoutController {

    private final ICheckoutService checkoutService;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<CheckoutResponse> getAllCheckouts() {
        return checkoutService.getAllCheckout();
    }

    @GetMapping("/getByNumber/{numberInvoice}")
    @ResponseStatus(HttpStatus.OK)
    public CheckoutResponse getByNumber(@PathVariable String numberInvoice) {
        return checkoutService.getByNumberInvoice(numberInvoice);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createInvoice(@RequestBody CheckoutRequest checkoutRequest) {
        checkoutService.createCheckout(checkoutRequest);
    }

    @DeleteMapping("/deleteProduct/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String numberInvoice) {
        checkoutService.deleteCheckout(numberInvoice);
    }
}
