package com.project.ecommerce.category.dto;

import com.project.ecommerce.utils.enums.CategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    private CategoryEnum name;
    private String description;
}
