package com.project.ecommerce.favorite.service;

import com.project.ecommerce.favorite.dto.request.FavoriteRequest;
import com.project.ecommerce.favorite.dto.response.FavoriteResponse;
import com.project.ecommerce.utils.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFavoriteService {

    FavoriteResponse addFavorite(FavoriteRequest favoriteRequest) throws ApiException;
    List<FavoriteResponse> getAllFavorites() throws ApiException;
}
