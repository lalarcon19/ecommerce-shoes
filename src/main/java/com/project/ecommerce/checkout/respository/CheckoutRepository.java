package com.project.ecommerce.checkout.respository;

import com.project.ecommerce.checkout.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

   Optional<Checkout> findByNumberInvoice(String numberInvoice);
    Checkout findCheckoutById(long id);
}
