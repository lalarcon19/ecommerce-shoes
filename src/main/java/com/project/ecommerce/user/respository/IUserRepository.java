package com.project.ecommerce.user.respository;

import com.project.ecommerce.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByDocument(String document);
    UserEntity findByIdUser (long idUser);

    Optional<UserEntity> findUserEntityByUsername(String username);
}
