package com.project.ecommerce.category.dto;

import com.project.ecommerce.utils.enums.CategoryEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {
    private CategoryEnum name;
    private String description;
}
