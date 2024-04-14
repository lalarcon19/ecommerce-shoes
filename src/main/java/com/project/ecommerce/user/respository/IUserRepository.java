package com.project.ecommerce.user.respository;

import com.project.ecommerce.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDocument(String document);
    User findByIdUser (long idUser);
}
