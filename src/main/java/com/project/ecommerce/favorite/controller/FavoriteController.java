package com.project.ecommerce.favorite.controller;

import com.project.ecommerce.favorite.dto.request.FavoriteRequest;
import com.project.ecommerce.favorite.dto.response.FavoriteResponse;
import com.project.ecommerce.favorite.service.IFavoriteService;
import com.project.ecommerce.utils.exception.ApiException;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/favorite")
@CrossOrigin(origins = "*",  methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = "*")
public class FavoriteController {

    @Autowired
    IFavoriteService favoriteService;

    @PostMapping("/add")
    public ResponseEntity<FavoriteResponse> addFavorites(
            @RequestBody FavoriteRequest favoriteRequest) throws ApiException {
        FavoriteResponse response = favoriteService.addFavorite(favoriteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAll/{idUser}")
    public ResponseEntity<List<FavoriteResponse>> getAllFavorite() throws ApiException {
        List<FavoriteResponse> favoriteResponse = favoriteService.getAllFavorites();
        return ResponseEntity.status(HttpStatus.OK).body(favoriteResponse);
    }

    @GetMapping("/getByIdFavorite/{idUser}")
    public ResponseEntity<FavoriteResponse> getFavorite(
            @PathVariable long idFavorite) throws ApiException {
        FavoriteResponse response = favoriteService.getById(idFavorite);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/deleteFavorite/{idUser}")
    public ResponseEntity<String> deleteProduct(@PathVariable long idFavorite) throws ApiException {
        favoriteService.deleteFavorite(idFavorite);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
