package com.project.ecommerce.user.controller;

import com.project.ecommerce.user.dto.request.WishlistRequest;
import com.project.ecommerce.user.dto.response.WishlistResponse;
import com.project.ecommerce.user.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin(origins = "*",  methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = "*")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<WishlistResponse> getAll()  {
        return wishlistService.getAll();
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WishlistResponse getAll(@PathVariable long id)  {
        return wishlistService.getById(id);
    }

    @PostMapping("/create/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable int id)  {
         wishlistService.create(id);
    }

    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody WishlistRequest request)  {
        wishlistService.addProduct(request);
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id)  {
        wishlistService.delete(id);
    }
}
