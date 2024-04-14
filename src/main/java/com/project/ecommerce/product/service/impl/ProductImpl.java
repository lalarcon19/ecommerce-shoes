package com.project.ecommerce.product.service.impl;

import com.project.ecommerce.product.entity.ProductEntity;
import com.project.ecommerce.utils.exception.ApiException;
import com.project.ecommerce.product.dto.request.ProductRequest;
import com.project.ecommerce.product.dto.response.ProductResponse;
import com.project.ecommerce.product.respository.IProductRepository;
import com.project.ecommerce.product.service.IProductService;
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
public class ProductImpl implements IProductService {

    public static final Logger logger = LoggerFactory.getLogger(ProductImpl.class);
    private final IProductRepository productRepository;
    private final ModelMapper mapper = new ModelMapper();

    public ProductImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public ProductResponse createProduct(ProductRequest productRequest) throws ApiException {
        logger.info("---El servidor ingresa al servicio para crear un producto----");
        try {
            Optional<ProductEntity> productName = productRepository.findByName(productRequest.getName());
            if (productName.isPresent()) {
                logger.info("----El producto a crear, ya existe en la tienda----");
                throw new ApiException("El producto ya existe en la tienda", HttpStatus.CONFLICT);
            }
            ProductEntity product = mapper.map(productRequest, ProductEntity.class);
            productRepository.saveAndFlush(product);
            logger.info("---Se guardo correctamente el producto en base de datos----");
            return mapper.map(product, ProductResponse.class);
        } catch (Exception e){
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ProductResponse> getAllProducts() throws ApiException {
        List<ProductEntity> listProduct;
        try {
            logger.info("----Entro al servicio para buscar productos existentes");
            listProduct = productRepository.findAll();
        }catch (Exception e){
            logger.info("---Error al realizar metodo de traer todos los productos-----");
            throw new ApiException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listProduct.isEmpty()){
            logger.info("----No hay productos guardados en la tienda virtual----");
            return Collections.emptyList();
        }
        logger.info("---Se obtuvieron los productos guardados---");
        return listProduct.stream()
                .map(product -> mapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getByName(String name) throws ApiException{
        try {
            logger.info("---Se conecto el servicio para traer producto por nombre---");
            Optional<ProductEntity> productByName = productRepository.findByName(name);
            if (productByName.isPresent()) {
                logger.info("---Se encontro el producto con nombre {} en base de datos---", name);
                ProductEntity product = productByName.get();
                return mapper.map(product, ProductResponse.class);
            }else {
                logger.info("---El producto con el nombre {} no se encontro en la tienda",name);
                throw  new ApiException("No se encontro producto con ese nombre", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.info("---Se produjo un error al buscar producto por su nombre---");
            throw new ApiException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ProductResponse updateProduct(long idProduct, ProductRequest productRequest) throws ApiException {
        try {
            logger.info("---Entro al servicio para actualizar producto---");
             ProductEntity product = productRepository.findProductByIdProduct(idProduct);
            if (product != null) {
                product.setName(productRequest.getName());
                product.setPrice(productRequest.getPrice());
                productRepository.saveAndFlush(product);
                logger.info("---Producto actualizado correctamente---");
                ProductResponse productResponse = mapper.map(product, ProductResponse.class);
                throw new ApiException("Producto actualizado correctamete", HttpStatus.OK);
            }else {
                logger.info("---El producto no fue encontrado para actualizar---");
                throw new ApiException("No se encontro producto para actualizar", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.info("---Se produjo un error al actualizar el producto");
            throw new ApiException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    public void deleteProduct(String name) throws ApiException {
        try{
            logger.info("---Entro al servicio para eliminar producto---");
            Optional<ProductEntity> productOptional = productRepository.findByName(name);
            if (productOptional.isPresent()){
                ProductEntity product = productOptional.get();
                productRepository.delete(product);
                logger.info("---El producto sellecionado, fue eliminado correctamente---");
                throw new ApiException("Producto eliminado correctamente", HttpStatus.OK);
            }else {
                logger.info("---El producto ya no existe---");
                throw new ApiException("El producto no existe", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.info("---Se produjo un error al eliminar el producto seleccionado----");
            throw new ApiException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
