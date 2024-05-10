package com.project.ecommerce.category.repository;

import com.project.ecommerce.category.entity.Category;
import com.project.ecommerce.utils.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryEnum name);
    Optional<Category> findById(long id);
}
