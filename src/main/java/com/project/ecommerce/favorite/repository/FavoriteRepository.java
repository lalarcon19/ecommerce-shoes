package com.project.ecommerce.favorite.repository;

import com.project.ecommerce.favorite.entities.FavoriteEntity;
import com.project.ecommerce.favorite.service.impl.FavoriteImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    FavoriteEntity findFavoriteByIdProduct(long id_favorite);
    Optional<FavoriteEntity> findByIdFavorite(long id_favorite);

}
