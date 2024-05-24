package com.project.ecommerce.product.service.impl;

import com.project.ecommerce.category.entity.Category;
import com.project.ecommerce.category.repository.CategoryRepository;
import com.project.ecommerce.product.entity.Product;
import com.project.ecommerce.utils.exception.ApiException;
import com.project.ecommerce.product.dto.request.ProductRequest;
import com.project.ecommerce.product.dto.response.ProductResponse;
import com.project.ecommerce.product.respository.ProductRepository;
import com.project.ecommerce.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImpl implements IProductService {

    public static final Logger logger = LoggerFactory.getLogger(ProductImpl.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public void create(ProductRequest productRequest) {
        logger.info("---- El servidor ingresa al servicio para crear un producto ----");
        Optional<Product> productName = productRepository.findByName(productRequest.getName());
        Optional<Category> category = categoryRepository.findByName(productRequest.getCategory());

        if (productName.isPresent()) {
            logger.info("---- El producto a crear, ya existe en la tienda ----");
            throw new ApiException("El producto ya existe en la tienda.", HttpStatus.CONFLICT);
        }

        if (category.isEmpty()) {
            logger.info("---- La categoria no existe en la tienda ----");
            throw new ApiException("La categoria no existe en la tienda.", HttpStatus.CONFLICT);
        }

        Product product = mapper.map(productRequest, Product.class);
        product.setCategory(category.get());
        productRepository.saveAndFlush(product);
        logger.info("---- Se guardo correctamente el producto en base de datos ----");
    }

    @Override
    public List<ProductResponse> getAll() {
        logger.info("---- Buscando productos existentes ----");
        List<Product> listProduct = productRepository.findAll();

        if (listProduct.isEmpty()) {
            logger.info("----No hay productos guardados en la tienda virtual----");
            return Collections.emptyList();
        }

        logger.info("---Se obtuvieron los productos guardados---");
        return listProduct.stream()
                .map(ProductImpl::productToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getByName(String name) {
        logger.info("---- Se conecto el servicio para traer producto por nombre ----");
        Optional<Product> productByName = productRepository.findByName(name);

        if (productByName.isEmpty()) {
            logger.info("---- No se encontro ----");
            throw new ApiException("No se encontro el producto.", HttpStatus.NOT_FOUND);
        }

        logger.info("---- Se encontro el producto ----");
        return productToResponse(productByName.get());
    }

    @Override
    public ProductResponse getById(long id) {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new ApiException("El producto no existe.", HttpStatus.BAD_GATEWAY);
        }

        return null;
    }

    @Override
    public void update(long id, ProductRequest productRequest) {
        logger.info("---- Entro al servicio para actualizar producto ----");
        Product product = productRepository.findProductById(id);

        if (product == null) {
            logger.info("---- El producto no fue encontrado para actualizar ----");
            throw new ApiException("No se encontro producto para actualizar", HttpStatus.NOT_FOUND);
        }

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        productRepository.saveAndFlush(product);
        logger.info("---- Producto actualizado correctamente ----");
    }

    @Override
    public void delete(Long id) {
        logger.info("---- Entro al servicio para eliminar producto ----");
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            logger.info("---- El producto ya no existe ----");
            throw new ApiException("El producto no existe.", HttpStatus.NOT_FOUND);
        }

        productRepository.delete(productOptional.get());
        logger.info("---- El producto seleccionado, fue eliminado correctamente ----");
    }

    public static ProductResponse productToResponse(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .category(entity.getCategory().getName())
                .build();
    }

}
