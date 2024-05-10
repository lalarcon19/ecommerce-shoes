package com.project.ecommerce.category.service;

import com.project.ecommerce.category.dto.CategoryRequest;
import com.project.ecommerce.category.dto.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    void create(CategoryRequest categoryRequest);

    CategoryResponse getById(int idCategory);

    List<CategoryResponse> getAll();

    void update(CategoryRequest categoryRequest);
}
