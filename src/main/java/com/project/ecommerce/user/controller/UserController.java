package com.project.ecommerce.user.controller;

import com.project.ecommerce.user.dto.request.UserRequest;
import com.project.ecommerce.user.dto.response.UserResponse;
import com.project.ecommerce.user.service.IUserService;
import com.project.ecommerce.utils.exception.ApiException;
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
@RequestMapping("/users")
@CrossOrigin(origins =  "*",  methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = "*")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAllProducts () throws ApiException{
        List<UserResponse> response = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<UserResponse> getProductByName (
            @PathVariable String document)throws ApiException{
        UserResponse response = userService.getByDocument(document);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/updateProduct/{idUser}")
    public ResponseEntity<UserResponse>  updateProduct(
            @PathVariable long idUser, @RequestBody UserRequest userRequest) throws ApiException{
        UserResponse response = userService.updateUser(idUser, userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/deleteProduct/{document}")
    public ResponseEntity<String> deleteProduct (@PathVariable String document)throws ApiException{
        userService.deleteUser(document);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

