package com.project.ecommerce.checkout.service.impl;

import com.project.ecommerce.checkout.dto.request.CheckoutRequest;
import com.project.ecommerce.checkout.dto.response.CheckoutResponse;
import com.project.ecommerce.checkout.entity.Checkout;
import com.project.ecommerce.checkout.respository.CheckoutRepository;
import com.project.ecommerce.checkout.service.ICheckoutService;
import com.project.ecommerce.product.entity.Product;
import com.project.ecommerce.product.respository.ProductRepository;
import com.project.ecommerce.user.entity.UserEntity;
import com.project.ecommerce.user.respository.UserRepository;
import com.project.ecommerce.utils.enums.Status;
import com.project.ecommerce.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.ecommerce.payments.service.impl.PaymentImpl.paymentToResponse;
import static com.project.ecommerce.user.service.impl.UserImpl.userToResponse;

@Service
@RequiredArgsConstructor
public class CheckoutImpl implements ICheckoutService {

    public static final Logger logger = LoggerFactory.getLogger(CheckoutImpl.class);
    private final CheckoutRepository checkoutRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public void createCheckout(CheckoutRequest checkoutRequest) {
        logger.info("---- El servidor ingresa al servicio para crear una factura ----");
        UserEntity user = userRepository.findById(checkoutRequest.getUserId());

        if (user == null) {
            logger.error("El usuario no existe.");
            throw new ApiException("El usuario no existe.", HttpStatus.BAD_REQUEST);
        }

        if (user.getPayment() == null) {
            logger.error("El usuario no tiene un metodo de pago asignado.");
            throw new ApiException("El usuario no tiene un metodo de pago asignado.", HttpStatus.BAD_REQUEST);
        }

        List<Product> product = productRepository.findByIdIn(checkoutRequest.getProducts());

        if (product.isEmpty()) {
            logger.error("Los productos no existen.");
            throw new ApiException("Los productos no existen", HttpStatus.BAD_REQUEST);
        }

        Checkout checkout = requestToCheckout(checkoutRequest);
        checkout.setNumberInvoice("12345678"); //crear metodo aleatorio
        checkout.setOrderStatus(Status.CREADO);
        checkout.setUser(user);
        checkout.setPayment(user.getPayment());
        checkout.setProduct(new HashSet<>(product));
        checkoutRepository.saveAndFlush(checkout);

        logger.info("---- Se creo correctamente la factura en base de datos ----");

    }

    @Override
    public List<CheckoutResponse> getAllCheckout() {
        logger.info("---- Entro al servicio para buscar facturas existentes ----");

        List<Checkout> listCheckout = checkoutRepository.findAll();

        if (listCheckout.isEmpty()) {
            logger.info("---- No hay facturas registradas con esa informacion ----");
            return Collections.emptyList();
        }

        logger.info("---- Se obtuvieron las facturas guardadas ----");

        return listCheckout.stream()
                .map(CheckoutImpl::checkoutToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CheckoutResponse getByNumberInvoice(String numberInvoice) {
        logger.info("---- Se conecto para traer facturas por fecha de creacion -----");

        Optional<Checkout> checkoutEntity = checkoutRepository.findByNumberInvoice(numberInvoice);

        if (checkoutEntity.isEmpty()) {
            logger.info("---- La factura con el numero {} no se encontro en base de datos ----", numberInvoice);
            throw new ApiException("No se encontro factura", HttpStatus.NOT_FOUND);
        }

        logger.info("---- Se encontro factura {} en base de datos ----", numberInvoice);
        return checkoutToResponse(checkoutEntity.get());
    }

    @Override
    public void deleteCheckout(String numberInvoice) {
        logger.info("---- Entro al servicio para eliminar factura ----");
        Optional<Checkout> checkout = checkoutRepository.findByNumberInvoice(numberInvoice);

        if (checkout.isEmpty()) {
            logger.info("la factura con el numero {}, no se encontre", numberInvoice);
            throw new ApiException("La factura no existe", HttpStatus.NOT_FOUND);
        }

        Checkout entity = checkout.get();
        checkoutRepository.delete(entity);
        logger.info("---- La factura se elimino ----");

    }

    public Checkout requestToCheckout(CheckoutRequest checkout) {
        return Checkout.builder()
                .totalPrice(checkout.getTotalPrice())
                .dateInvoice(new Date())
                .description(checkout.getDescription())
                .build();
    }

    public static CheckoutResponse checkoutToResponse(Checkout checkout) {
        return CheckoutResponse.builder()
                .id(checkout.getId())
                .totalPrice(checkout.getTotalPrice())
                .dateInvoice(checkout.getDateInvoice())
                .description(checkout.getDescription())
                .payment(paymentToResponse(checkout.getPayment()))
                .build();
    }
}
