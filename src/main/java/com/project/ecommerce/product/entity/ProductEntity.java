package com.project.ecommerce.product.entity;


import com.project.ecommerce.category.entity.CategoryEntity;
import com.project.ecommerce.favorite.entities.FavoriteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCT")
    private long idProduct;

    @Column(name = "NOMBRE")
    public String name;

    @Column(name = "PRECIO")
    public int price;

    @Column(name = "CATEGORIA_ID")
    public int category_id;

    @Column(name = "USER_FINAL")
    public int userFinal;

    @OneToMany(mappedBy = "product")
    private Set<FavoriteEntity> favoriteEntity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
