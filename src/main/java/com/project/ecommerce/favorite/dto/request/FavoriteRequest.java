package com.project.ecommerce.favorite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequest {

    private int idFavorite;

    public String nombreProducto;

    public int priceProduct;

    public int productId;
}
