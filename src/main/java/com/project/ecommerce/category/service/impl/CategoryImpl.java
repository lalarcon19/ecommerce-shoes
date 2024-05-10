package com.project.ecommerce.category.service.impl;

import com.project.ecommerce.category.dto.CategoryResponse;
import com.project.ecommerce.category.entity.Category;
import com.project.ecommerce.category.repository.CategoryRepository;
import com.project.ecommerce.category.dto.CategoryRequest;
import com.project.ecommerce.category.service.CategoryService;
import com.project.ecommerce.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CategoryImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void create(CategoryRequest categoryRequest) {
        Optional<Category> category = categoryRepository.findByName(categoryRequest.getName());

        if (category.isPresent()) {
            throw new ApiException("La categoria ya existe", HttpStatus.BAD_GATEWAY);
        }
        Category entity = Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build();
        categoryRepository.saveAndFlush(entity);
    }

    @Override
    public CategoryResponse getById(int idCategory) {
        Category category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new ApiException("La categoria no existe", HttpStatus.BAD_GATEWAY));
        return categoryToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryImpl::categoryToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void update(CategoryRequest categoryRequest) {
        Optional<Category> category = categoryRepository.findByName(categoryRequest.getName());

        if (category.isEmpty()) {
            throw new ApiException("La categoria no existe", HttpStatus.BAD_GATEWAY);
        }
        categoryRepository.saveAndFlush(Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build());

    }

    public static CategoryResponse categoryToResponse(Category category) {
        return CategoryResponse.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
