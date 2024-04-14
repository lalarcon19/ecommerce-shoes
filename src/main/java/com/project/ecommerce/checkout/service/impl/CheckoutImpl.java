package com.project.ecommerce.checkout.service.impl;

import com.project.ecommerce.checkout.dto.request.CheckoutRequest;
import com.project.ecommerce.checkout.dto.response.CheckoutResponse;
import com.project.ecommerce.checkout.entities.CheckoutEntity;
import com.project.ecommerce.checkout.respository.CheckoutRepository;
import com.project.ecommerce.checkout.service.ICheckoutService;
import com.project.ecommerce.utils.exception.ApiException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckoutImpl implements ICheckoutService {

    public static final Logger logger = LoggerFactory.getLogger(CheckoutImpl.class);
    private final CheckoutRepository checkoutRepository;
    private final ModelMapper mapper = new ModelMapper();

    public CheckoutImpl(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }


    @Override
    public CheckoutResponse createCheckout(CheckoutRequest checkoutRequest) throws ApiException {
        logger.info("---El servidor ingresa al servicio para crear una factura---");
        try{
           CheckoutEntity checkout = mapper.map(checkoutRequest, CheckoutEntity.class);
           checkoutRepository.saveAndFlush(checkout);
           logger.info("---Se creo correctamente la factura en base de datos---");
           return mapper.map(checkout, CheckoutResponse.class);
        }catch (Exception e){
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CheckoutResponse> getAllCheckout() throws ApiException {
        List<CheckoutEntity> listCheckout;
        try {
            logger.info("---Entro al servicio para buscar facturas existentes---");
            listCheckout = checkoutRepository.findAll();
        }catch (Exception e){
            logger.info("---Error al buscar facturas en base de datos---");
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listCheckout.isEmpty()){
            logger.info("---No hay facturas registradas con esa informacion---");
            return Collections.emptyList();
        }
        logger.info("---Se obtuvieron las facturas guardadas---");
        return listCheckout.stream()
                .map(checkoutEntity -> mapper.map(checkoutEntity, CheckoutResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CheckoutResponse getByNumberInvoice(String numberInvoice) throws ApiException {
        try {
            logger.info("---Se conecto para traer facturas por fecha de cracion----");
            Optional<CheckoutEntity> checkoutEntity = checkoutRepository.findByNumberInvoice(numberInvoice);
            if (checkoutEntity.isPresent()){
                logger.info("---Se encontro factura {} en base de datos---", numberInvoice);
                CheckoutEntity checkout = checkoutEntity.get();
                return mapper.map(checkout, CheckoutResponse.class);
            }else{
                logger.info("---La factura con el numero {} no se encontro en base de datos---", numberInvoice);
                throw new ApiException("No se encontro factura", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.info("---Se produjo un error al buscar factura---");
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CheckoutResponse updateCheckout(long idInvoice, CheckoutRequest request) throws ApiException {
        try {
            logger.info("---Entro al servicio para actualizar factura ---");
            CheckoutEntity checkoutEntity = checkoutRepository.findCheckoutByIdInvoice(idInvoice);
            if (checkoutEntity != null){
                checkoutEntity.setText(request.getText());
                checkoutEntity.setPayment(request.getPayment());
                checkoutEntity.setTotalPrice(request.getTotalPrice());
                checkoutRepository.saveAndFlush(checkoutEntity);
                logger.info("---Factura actualizada correctamente---");
                CheckoutResponse response = mapper.map(checkoutEntity, CheckoutResponse.class);
                throw new ApiException("Factura acualizada", HttpStatus.OK);
            }else {
                logger.info("---La factura  no fue encontrada para actualizar---");
                throw new ApiException("No se encontro factura para actualizar", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.info("---Se produjo un error al actualizar factura---");
            throw new ApiException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteCheckout(String numberInvoice) throws ApiException {
        try {
            logger.info("---Entro al servicio para eliminar factura---");
            Optional<CheckoutEntity> checkout = checkoutRepository.findByNumberInvoice(numberInvoice);
            if (checkout.isPresent()){
                CheckoutEntity entity = checkout.get();
                checkoutRepository.delete(entity);
                logger.info("---La factura se elimino---");
                throw new ApiException("Factura eliminada", HttpStatus.OK);
            }else {
                logger.info("la factura con el numero {}, no se encontre",numberInvoice);
                throw new ApiException("La factura no existe",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.info("---Se produjo un error al eliminar una factura---");
            throw new ApiException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
