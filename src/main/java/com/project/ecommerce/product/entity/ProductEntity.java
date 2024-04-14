package com.project.ecommerce.product.entity;


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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "producto")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private long idProduct;

    @Column(name = "NOMBRE")
    public String name;

    @Column(name = "PRECIO")
    public int price;

    @Column(name = "CATEGORIA_ID")
    public int category_id;

    @Column(name = "USER_FINAL")
    public int userFinal;
}
