package com.project.ecommerce.favorite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteResponse {
    private int idFavorite;

    public String nameProduct;

    public int priceProduct;

    public int productId;
}
