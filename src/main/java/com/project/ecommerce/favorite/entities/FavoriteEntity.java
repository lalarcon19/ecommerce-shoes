package com.project.ecommerce.favorite.entities;

import com.project.ecommerce.product.entity.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FAVORITE")
public class FavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FAVORITOS")
    private int idFavorite;

    @Column(name = "NOMBRE_PRODUCTO")
    public String nameProduct;

    @Column(name = "PRECIO_PRODUCTO")
    public int priceProduct;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

}
