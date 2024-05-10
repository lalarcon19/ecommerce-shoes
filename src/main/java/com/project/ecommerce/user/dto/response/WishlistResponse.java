package com.project.ecommerce.user.dto.response;

import com.project.ecommerce.product.dto.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {
    private int idFavorite;
    private UserResponse user;
    public List<ProductResponse> product;
}
