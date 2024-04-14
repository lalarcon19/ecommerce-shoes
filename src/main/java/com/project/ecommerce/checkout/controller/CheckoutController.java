package com.project.ecommerce.checkout.controller;

import com.project.ecommerce.checkout.dto.request.CheckoutRequest;
import com.project.ecommerce.checkout.dto.response.CheckoutResponse;
import com.project.ecommerce.checkout.service.ICheckoutService;
import com.project.ecommerce.product.dto.request.ProductRequest;
import com.project.ecommerce.product.dto.response.ProductResponse;
import com.project.ecommerce.utils.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/checkout")
@CrossOrigin(origins = "*",  methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = "*")
public class CheckoutController {

    @Autowired
    ICheckoutService checkoutService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> createInvoice (
            @RequestBody CheckoutRequest checkoutRequest) throws ApiException {
        CheckoutResponse response = checkoutService.createCheckout(checkoutRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CheckoutResponse>> getAllCheckouts () throws ApiException{
        List<CheckoutResponse> checkoutResponse = checkoutService.getAllCheckout();
        return ResponseEntity.status(HttpStatus.OK).body(checkoutResponse);
    }

    @GetMapping("/getByNumberInvoice/{numberInvoice}")
    public ResponseEntity<CheckoutResponse> getCheckoutByNumber (
            @PathVariable String numberInvoice)throws ApiException{
        CheckoutResponse response = checkoutService.getByNumberInvoice(numberInvoice);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/updateProduct/{idProduct}")
    public ResponseEntity<CheckoutResponse>  updateProduct(
            @PathVariable long idInvoice, @RequestBody CheckoutRequest checkoutRequest) throws ApiException{
        CheckoutResponse checkoutResponse = checkoutService.updateCheckout(idInvoice, checkoutRequest);
        return ResponseEntity.status(HttpStatus.OK).body(checkoutResponse);
    }

    @DeleteMapping("/deleteProduct/{name}")
    public ResponseEntity<String> deleteProduct (@PathVariable String numberInvoice)throws ApiException{
        checkoutService.deleteCheckout(numberInvoice);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
