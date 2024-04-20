package com.project.ecommerce.checkout.entities;

import com.project.ecommerce.payments.enttity.PaymentsEntity;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CHECKOUT")
public class CheckoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FACTURA")
    private int idInvoice;

    @Column(name = "PRECIO_TOTAL")
    private int totalPrice;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status orderStatus;

    @Column(name = "FECHA_FACTURA")
    public Date dateInvoice;

    @Column(name = "NUMERO_FACTURA")
    public String numberInvoice;

    @Column(name = "DESCRIPCION")
    public TextArea text;

    @Column(name = "USUARIO_ID")
    public int user_id;

    @Column(name = "PAGO_ID")
    public int payment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentsEntity payments;
}
