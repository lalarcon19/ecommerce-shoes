package com.project.ecommerce.payments.dto.response;

import com.project.ecommerce.user.dto.response.UserResponse;
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
public class PaymentResponse {

    private long id;

    private PaymentsEnum paymentMethod;

    private FranchisesEnum franchises;

    private String cardNumber;

}
