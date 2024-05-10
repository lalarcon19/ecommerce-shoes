package com.project.ecommerce.payments.dto.request;


import com.project.ecommerce.utils.enums.FranchisesEnum;
import com.project.ecommerce.utils.enums.PaymentsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentRequest {

    private PaymentsEnum paymentMethod;

    private FranchisesEnum franchises;

    private String cardNumber;

    private int userId;
}
