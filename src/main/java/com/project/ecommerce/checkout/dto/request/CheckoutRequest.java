package com.project.ecommerce.checkout.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {

    private int idInvoice;

    private int totalPrice;

    public Date dateInvoice;

    public String nameInvoice;

    public TextArea text;

    public int user_id;

    public int payment;
}
