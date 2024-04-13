package com.project.ecommerce.products.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "producto")
public class ProductEntity {
    @Id
    @GeneratedValue
    public long id;

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
