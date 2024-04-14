package com.project.ecommerce.user.service;

import com.project.ecommerce.user.dto.request.AuthRegisterRequest;
import com.project.ecommerce.user.dto.request.UserRequest;
import com.project.ecommerce.user.dto.response.AuthResponse;
import com.project.ecommerce.user.dto.response.UserResponse;
import com.project.ecommerce.utils.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    AuthResponse createUser (AuthRegisterRequest authRegisterRequest) throws ApiException;
    List<UserResponse> getAllUsers () throws ApiException;
    UserResponse getByDocument (String Document) throws ApiException;
    UserResponse updateUser(long idUser, UserRequest userRequest) throws ApiException;
    void deleteUser(String document) throws ApiException;
}
