package com.project.ecommerce.product.service;

import com.project.ecommerce.product.dto.request.ProductRequest;
import com.project.ecommerce.product.dto.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    void create(ProductRequest productRequest);

    List<ProductResponse> getAll();

    ProductResponse getByName(String name);

    ProductResponse getById(long id);

    void update(long id, ProductRequest productRequest);

    void delete(Long id);

}
