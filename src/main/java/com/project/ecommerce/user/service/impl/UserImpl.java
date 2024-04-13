package com.project.ecommerce.user.service.impl;

import com.project.ecommerce.user.dto.request.UserRequest;
import com.project.ecommerce.user.dto.response.UserResponse;
import com.project.ecommerce.user.entity.UserEntity;
import com.project.ecommerce.user.respository.IUserRepository;
import com.project.ecommerce.user.service.IUserService;
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
public class UserImpl implements IUserService {

    public static final Logger logger = LoggerFactory.getLogger(UserImpl.class);
    private final IUserRepository userRepository;
    private final ModelMapper mapper = new ModelMapper();

    public UserImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) throws ApiException {
        logger.info("---El servidor ingresa al servicio para crear un usuario");
        try {
            Optional<UserEntity> userDocument = userRepository.findByDocument(userRequest.getDocument());
            if (userDocument.isPresent()){
                logger.info("---El usuario ya existe---");
                throw new ApiException("El usuario ya existe", HttpStatus.CONFLICT);
            }
            UserEntity user = mapper.map(userRequest, UserEntity.class);
            userRepository.saveAndFlush(user);
            logger.info("---Se guardo correctamente el usuario en base de datos");
            return mapper.map(user, UserResponse.class);
        }catch (Exception e){
            throw new ApiException("Se produjo un error (createUser)",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UserResponse> getAllUsers() throws ApiException {
        List<UserEntity> listUser;
        try{
            logger.info("---Entro al servicio para buscar usuarios registrados---");
            listUser = userRepository.findAll();
        }catch (Exception e){
            logger.info("---Error al realizar metodo de traer todos los usuarios---");
            throw new ApiException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listUser.isEmpty()){
            logger.info("---No hay usuarios registrados hasta el momento---");
            return Collections.emptyList();
        }
        logger.info("---No hay usuarios registrados---");
        return listUser.stream()
                .map(userEntity -> mapper.map(userEntity, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getByDocument(String document) throws ApiException {
        try {
            logger.info("---Se conecto al servicio para buscar por documento un usuario---");
            Optional<UserEntity> userByDocument = userRepository.findByDocument(document);
            if (userByDocument.isPresent()){
                logger.info("---Se encontro useario con documento {} en base de datos----", document);
                UserEntity user = userByDocument.get();
                return mapper.map((user, UserResponse.class);
            }else {
                logger.info("---El usuario con el documento {} no se encontro registrado", document);
                throw new ApiException("el usuario no se encuentra registrado", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.info("---Se produjo un erro al buscar usuario por documento---");
            throw new ApiException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse updateUser(long idUser, UserRequest userRequest) throws ApiException {
        try{
            logger.info("---Entro al servicio para actualizar usuario---");
            UserEntity user = userRepository.findByIdUser(idUser);
            if (user != null){
            user.setName(userRequest.getName());
            user.setLastName(userRequest.getLastName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            user.setType_document(userRequest.getType_document());
            user.setDocument(userRequest.getDocument());
            user.setAddress(userRequest.getAddress());
            userRepository.saveAndFlush(user);
            logger.info("---El usuario fue actualizado correctamente---");
            UserResponse response = mapper.map(user, UserResponse.class);
            throw new ApiException("Usuario actualizado correctamente",HttpStatus.OK);
            }else {
                logger.info(("---El usuario no fue encontrado para actualizar---"));
                throw new ApiException("No se encontro el usuario para actualizar", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            logger.info("---Se produjo un error al actualizar el usuario---");
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteUser(String document) throws ApiException {
        try {
            logger.info("---Entro al servicio para eliminar usuario---");
            Optional<UserEntity> userEntityOptional = userRepository.findByDocument(document);
            if(userEntityOptional.isPresent()){
                UserEntity user = userEntityOptional.get();
                userRepository.delete(user);
                logger.info("---El usuario selecionado, fue eliminado correctamente---");
                throw new ApiException("usuario eliminado correctamente", HttpStatus.OK);
            }else {
                logger.info("---El usuario ya no existe----");
                throw new ApiException("El usuario no existe", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.info("---Se produjo un error al eliminar el usuario seleccionado---");
            throw new ApiException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
