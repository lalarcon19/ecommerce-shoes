package com.project.ecommerce.user.service;

import com.project.ecommerce.user.dto.request.WishlistRequest;
import com.project.ecommerce.user.dto.response.WishlistResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishlistService {

    void create(int id);

    void addProduct(WishlistRequest wishlistRequest);

    List<WishlistResponse> getAll();

    WishlistResponse getById(long id);

    void delete(long id);
}
