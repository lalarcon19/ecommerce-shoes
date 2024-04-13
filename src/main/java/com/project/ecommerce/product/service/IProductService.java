package com.project.ecommerce.product.service;

import com.project.ecommerce.utils.exception.ApiException;
import com.project.ecommerce.product.dto.request.ProductRequest;
import com.project.ecommerce.product.dto.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    ProductResponse createProduct (ProductRequest productRequest) throws ApiException;

    List<ProductResponse> getAllProducts() throws ApiException;

    ProductResponse getByName(String name) throws ApiException;

    ProductResponse updateProduct(long idProduct, ProductRequest productRequest) throws ApiException;

    void deleteProduct(String name)throws ApiException;

}
