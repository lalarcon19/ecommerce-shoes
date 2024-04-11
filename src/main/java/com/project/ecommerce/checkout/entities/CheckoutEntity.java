package com.project.ecommerce.checkout.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "FECHA_FACTURA")
    public Date dateInvoice;

    @Column(name = "NUMERO_FACTURA")
    public String nameInvoice;

    @Column(name = "DESCRIPCION")
    public TextArea text;

    @Column(name = "USUARIO_ID")
    public int user_id;

    @Column(name = "PAGO_ID")
    public int payment;
}
