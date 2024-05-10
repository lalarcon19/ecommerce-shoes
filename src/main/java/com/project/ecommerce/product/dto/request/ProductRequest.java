package com.project.ecommerce.product.dto.request;

import com.project.ecommerce.utils.enums.CategoryEnum;
import lombok.Getter;

@Getter
public class ProductRequest {
    public String name;
    public int price;
    public CategoryEnum category;
    public int userFinal;
}
