package com.project.ecommerce.checkout.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {

    private int totalPrice;

    private String description;

    private int userId;

    private List<Long> products;

}
