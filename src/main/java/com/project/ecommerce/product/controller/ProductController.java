package com.project.ecommerce.product.controller;

import com.project.ecommerce.utils.exception.ApiException;
import com.project.ecommerce.product.dto.request.ProductRequest;
import com.project.ecommerce.product.dto.response.ProductResponse;
import com.project.ecommerce.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/products")
@CrossOrigin(origins = "*",  methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = "*")
public class ProductController {

    @Autowired
    IProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct (
            @RequestBody ProductRequest productRequest) throws ApiException{
        ProductResponse response = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponse>> getAllProducts () throws ApiException{
        List<ProductResponse> productResponse = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<ProductResponse> getProductByName (
            @PathVariable String name)throws ApiException{
        ProductResponse response = productService.getByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/updateProduct/{idProduct}")
    public ResponseEntity<ProductResponse>  updateProduct(
            @PathVariable long idProduct, @RequestBody ProductRequest productRequest) throws ApiException{
        ProductResponse response = productService.updateProduct(idProduct, productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/deleteProduct/{name}")
    public ResponseEntity<String> deleteProduct (@PathVariable String name)throws ApiException{
        productService.deleteProduct(name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
