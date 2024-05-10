package com.project.ecommerce.product.dto.response;

import com.project.ecommerce.utils.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private long id;
    public String name;
    public int price;
    public CategoryEnum category;
}
