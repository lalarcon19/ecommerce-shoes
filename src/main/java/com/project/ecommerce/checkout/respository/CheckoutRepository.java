package com.project.ecommerce.checkout.respository;

import com.project.ecommerce.checkout.entities.CheckoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface CheckoutRepository extends JpaRepository<CheckoutEntity, Long> {

   Optional<CheckoutEntity> findByNumberInvoice(String numberInvoice);
    CheckoutEntity findCheckoutByIdInvoice(long idInvoice);
}
