package com.project.ecommerce.payments.enttity;

import com.project.ecommerce.checkout.entities.CheckoutEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "PAYMENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPayment;

    @Column(name = "NAME_PAYMENT")
    private String namePayment;

    @OneToMany(mappedBy = "payment")
    private Set<CheckoutEntity> checkout;
}
