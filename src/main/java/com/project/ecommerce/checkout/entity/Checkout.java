package com.project.ecommerce.checkout.entity;

import com.project.ecommerce.payments.entity.Payment;
import com.project.ecommerce.product.entity.Product;
import com.project.ecommerce.user.entity.UserEntity;
import com.project.ecommerce.utils.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CHECKOUT")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status orderStatus;

    @Column(name = "date_invoice")
    public Date dateInvoice;

    @Column(name = "number_invoice")
    public String numberInvoice;

    @Column(name = "description")
    public String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany
    private Set<Product> product;

    @OneToOne
    private Payment payment;
}
