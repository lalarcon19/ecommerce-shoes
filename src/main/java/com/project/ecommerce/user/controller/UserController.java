package com.project.ecommerce.user.controller;

import com.project.ecommerce.user.dto.request.AuthLoginRequest;
import com.project.ecommerce.user.dto.request.AuthRegisterRequest;
import com.project.ecommerce.user.dto.request.UserRequest;
import com.project.ecommerce.user.dto.response.AuthResponse;
import com.project.ecommerce.user.dto.response.UserResponse;
import com.project.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAll()  {
        return userService.getAllUsers();
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getById(
            @PathVariable long id)  {
        return userService.getById(id);
    }

    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse register(@RequestBody @Valid AuthRegisterRequest authRegisterRequest)  {
        return userService.register(authRegisterRequest);
    }

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return userService.login(userRequest);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @PathVariable long id, @RequestBody UserRequest userRequest)  {
        userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long id)  {
        userService.deleteUser(id);
    }
}

