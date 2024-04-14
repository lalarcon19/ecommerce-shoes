package com.project.ecommerce.product.respository;

import com.project.ecommerce.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findProductByIdProduct(long idProduct);
    Optional<ProductEntity> findByName(String name);
}
