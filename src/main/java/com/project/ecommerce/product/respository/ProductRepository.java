package com.project.ecommerce.product.respository;

import com.project.ecommerce.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(long id);

    Optional<Product> findByName(String name);

    List<Product> findByIdIn(List<Long> productIds);
}
