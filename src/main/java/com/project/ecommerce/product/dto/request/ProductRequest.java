package com.project.ecommerce.product.dto.request;

import com.project.ecommerce.utils.enums.CategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private int price;
    private CategoryEnum category;
    private String img;
}
