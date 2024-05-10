package com.project.ecommerce.user.respository;

import com.project.ecommerce.user.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findById(long id);
    Wishlist findByUserId(int userId);
}
