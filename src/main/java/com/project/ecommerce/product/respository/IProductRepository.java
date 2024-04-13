package com.project.ecommerce.product.respository;

import com.project.ecommerce.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Product findProductByIdProduct(long idProduct);
    Optional<Product> findByName(String name);
}
