package com.project.ecommerce.utils.exception.controller;

import com.project.ecommerce.utils.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Map<String, String>> handlerException(ApiException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("message: ", exception.getMessage());
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }
}
