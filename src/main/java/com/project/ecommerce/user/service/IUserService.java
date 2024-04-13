package com.project.ecommerce.user.service;

import com.project.ecommerce.product.dto.response.ProductResponse;
import com.project.ecommerce.user.dto.request.UserRequest;
import com.project.ecommerce.user.dto.response.UserResponse;
import com.project.ecommerce.utils.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    UserResponse createUser (UserRequest userRequest) throws ApiException;
    List<UserResponse> getAllUsers () throws ApiException;
    UserResponse getByDocument (String Document) throws ApiException;
    UserResponse updateUser(long idUser, UserRequest userRequest) throws ApiException;
    void deleteUser(String document) throws ApiException;
}
