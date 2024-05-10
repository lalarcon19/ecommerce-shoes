package com.project.ecommerce.payments.entity;

import com.project.ecommerce.user.entity.UserEntity;
import com.project.ecommerce.utils.enums.FranchisesEnum;
import com.project.ecommerce.utils.enums.PaymentsEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "PAYMENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentsEnum paymentMethod;

    @Column(name = "franchise_name")
    @Enumerated(EnumType.STRING)
    private FranchisesEnum franchises;

    @Column(name = "card_number")
    private String cardNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
