package com.project.ecommerce.user.service;

import com.project.ecommerce.user.dto.request.AuthLoginRequest;
import com.project.ecommerce.user.dto.request.AuthRegisterRequest;
import com.project.ecommerce.user.dto.request.UserRequest;
import com.project.ecommerce.user.dto.response.AuthResponse;
import com.project.ecommerce.user.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    AuthResponse login(AuthLoginRequest authLoginRequest);

    AuthResponse register(AuthRegisterRequest authRegisterRequest);

    List<UserResponse> getAllUsers();

    UserResponse getById(long id);

    void updateUser(long idUser, UserRequest userRequest);

    void deleteUser(long id);
}
