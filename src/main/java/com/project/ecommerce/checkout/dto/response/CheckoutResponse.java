package com.project.ecommerce.checkout.dto.response;

import com.project.ecommerce.payments.dto.response.PaymentResponse;
import com.project.ecommerce.payments.entity.Payment;
import com.project.ecommerce.user.dto.response.UserResponse;
import com.project.ecommerce.utils.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutResponse {
    private int id;

    private int totalPrice;

    private Status orderStatus;

    public Date dateInvoice;

    public String numberInvoice;

    public String description;

    public UserResponse userId;

    public PaymentResponse payment;
}
