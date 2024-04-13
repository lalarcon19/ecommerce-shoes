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





}
