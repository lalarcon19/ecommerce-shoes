package com.project.ecommerce.user.service.impl;

import com.project.ecommerce.product.respository.ProductRepository;
import com.project.ecommerce.product.service.impl.ProductImpl;
import com.project.ecommerce.user.dto.request.WishlistRequest;
import com.project.ecommerce.user.dto.response.UserResponse;
import com.project.ecommerce.user.dto.response.WishlistResponse;
import com.project.ecommerce.user.respository.WishlistRepository;
import com.project.ecommerce.user.entity.Wishlist;
import com.project.ecommerce.user.service.WishlistService;
import com.project.ecommerce.product.entity.Product;
import com.project.ecommerce.user.entity.UserEntity;
import com.project.ecommerce.user.respository.UserRepository;
import com.project.ecommerce.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.ecommerce.user.service.impl.UserImpl.userToResponse;

@Service
@RequiredArgsConstructor
public class WishlistImpl implements WishlistService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistImpl.class);
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    //@Override
    public void create(int id) {
        logger.info("---- Creando lista de deseos ----");
        Wishlist wishlist = new Wishlist();

        UserEntity user = userRepository.findById(id);
        if (user == null) {
            logger.info("Hubo un error.");
            throw new ApiException("El Usuario no existe", HttpStatus.BAD_REQUEST);
        }
        logger.info("---- Asignando usuario a la lista de deseos ----");

        wishlist.setUser(user);
        wishlist.setProducts(Collections.emptySet());
        wishlistRepository.saveAndFlush(wishlist);

        logger.info("Lista de deseos creada.");
    }

    @Override
    public void addProduct(WishlistRequest wishlistRequest) {
        logger.info("---- Servicio para agregar un producto a lista de deseos ----");
        Product product = productRepository.findProductById(wishlistRequest.getProductId());
        if (product == null) {
            logger.error("Hubo un error.");
            throw new ApiException("El producto no existe", HttpStatus.BAD_REQUEST);
        }

        Wishlist wishlist = wishlistRepository.findByUserId(wishlistRequest.getUserId());
        if (wishlist == null) {
            logger.error("Hubo un error.");
            throw new ApiException("La Lista no existe", HttpStatus.BAD_REQUEST);
        }

        logger.info("---- Agregando producto a la lista ----");
        product.setWishlist(wishlist);
        productRepository.saveAndFlush(product);
        wishlist.getProducts().add(product);
        wishlistRepository.saveAndFlush(wishlist);

        logger.info("Se agrega el producto correctamente.");
    }

    @Override
    public List<WishlistResponse> getAll() {
        logger.info("---- Entro al servicio para buscar favoritos seleccionados ----");
        List<Wishlist> listEntity = wishlistRepository.findAll();

        if (listEntity.isEmpty()) {
            logger.info("---- No hay productos guardados en favoritos ----");
            return Collections.emptyList();
        }

        logger.info("---- Se obtuvieron los productos guardados ----");
        return listEntity.stream()
                .map(this::favoriteToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public WishlistResponse getById(long id) {
        Optional<Wishlist> entity = wishlistRepository.findById(id);
        if (entity.isEmpty()) {
            throw new ApiException("La lista no existe.", HttpStatus.BAD_GATEWAY);
        }
        return favoriteToResponse(entity.get());
    }

    @Override
    public void delete(long id) {
        logger.info("---- Entro al servicio para eliminar producto de la lista del usuario ----");
        Optional<Wishlist> wishlist = wishlistRepository.findById(id);

        if (wishlist.isEmpty()) {
            logger.info("---- El producto no existe en la lista ----");
            throw new ApiException("El producto no existe en la lista", HttpStatus.NOT_FOUND);
        }

        wishlistRepository.delete(wishlist.get());
        logger.info("---- El producto seleccionado, fue eliminado correctamente ----");
    }

    public WishlistResponse favoriteToResponse(Wishlist entity) {
        return WishlistResponse.builder()
                .idFavorite(entity.getId())
                .product(entity.getProducts().stream()
                        .map(ProductImpl::productToResponse)
                        .collect(Collectors.toList()) )
                .user(userToResponse(entity.getUser()))
                .build();
    }


}
