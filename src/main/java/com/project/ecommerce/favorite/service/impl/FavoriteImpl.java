package com.project.ecommerce.favorite.service.impl;

import com.project.ecommerce.favorite.dto.request.FavoriteRequest;
import com.project.ecommerce.favorite.dto.response.FavoriteResponse;
import com.project.ecommerce.favorite.repository.FavoriteRepository;
import com.project.ecommerce.favorite.entities.FavoriteEntity;
import com.project.ecommerce.favorite.service.IFavoriteService;
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
public class FavoriteImpl implements IFavoriteService {

    public static final Logger logger = LoggerFactory.getLogger(FavoriteImpl.class);
    public final FavoriteRepository favoriteRepository;
    private final ModelMapper mapper = new ModelMapper();

    public FavoriteImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public FavoriteResponse addFavorite(FavoriteRequest favoriteRequest) throws ApiException {
        logger.info("---El servidor ingresa al servicio para traer un producto favorito----");
        try {
            Optional<FavoriteEntity> favoriteEntity = favoriteRepository.findByIdFavorite(favoriteRequest.getIdFavorite());
            if (favoriteEntity.isPresent()) {
                logger.info("----El producto a agregar, ya existe en la lista de favoritos----");
                throw new ApiException("El producto ya existe en la lista de favotritos", HttpStatus.CONFLICT);
            }
            FavoriteEntity favorite = mapper.map(favoriteRequest, FavoriteEntity.class);
            favoriteRepository.saveAndFlush(favorite);
            logger.info("---Se agrego correctamente producto a la lista de favoritos----");
            return mapper.map(favorite, FavoriteResponse.class);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<FavoriteResponse> getAllFavorites() throws ApiException {
        List<FavoriteEntity> listEntity;
        try {
            logger.info("----Entro al servicio para buscar favoritos seleccionados");
            listEntity = favoriteRepository.findAll();
        } catch (Exception e) {
            logger.info("---Error al realizar metodo de optener favoritos -----");
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listEntity.isEmpty()) {
            logger.info("----No hay productos guardados enm favoritos----");
            return Collections.emptyList();
        }
        logger.info("---Se obtuvieron los productos guardados---");
        return listEntity.stream()
                .map(favoriteEntity -> mapper.map(favoriteEntity, FavoriteResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public FavoriteResponse getById(long idFavorite) throws ApiException {
        try{
            logger.info("---Se conecto para traer los productos favoritos del usuario");
            Optional<FavoriteEntity> favoriteById = favoriteRepository.findByIdFavorite(idFavorite);
            if(favoriteById.isPresent()){
                logger.info("Se ubico producto en la lista de favoritos del usuario");
                FavoriteEntity favorite = favoriteById.get();
                return mapper.map(favorite, FavoriteResponse.class);
            }else{
                logger.info("---No se encontro producto en la lista de favorito del usuario con ese nombre");
                throw new ApiException("No se encontro producto en favoritos", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.info("---Se produjo un error al buscar producto en la lista de favoritos");
            throw  new ApiException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteFavorite(long idFavorite) throws ApiException {
        try{
            logger.info("---Entro al servicio para eliminar producto de la lista del usuario---");
            Optional<FavoriteEntity> favoriteOptional = favoriteRepository.findByIdFavorite(idFavorite);
            if (favoriteOptional.isPresent()){
                FavoriteEntity favorite = favoriteOptional.get();
                favoriteRepository.delete(favorite);
                logger.info("---EL producto seleccionado, fue eliminado correctamente---");
                throw new ApiException("Producto eliminado correctamente", HttpStatus.OK);
            }else{
                logger.info("---El producto ya no existe en la lista");
                throw new ApiException("El producto ya no existe en la lista",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.info("---Se produjo un error al eliminar producto seleccionado---");
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
