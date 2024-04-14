package com.project.ecommerce.user.service.impl;

import com.project.ecommerce.user.dto.request.AuthLoginRequest;
import com.project.ecommerce.user.dto.request.AuthRegisterRequest;
import com.project.ecommerce.user.dto.request.UserRequest;
import com.project.ecommerce.user.dto.response.AuthResponse;
import com.project.ecommerce.user.dto.response.UserResponse;
import com.project.ecommerce.user.entity.RoleEntity;
import com.project.ecommerce.user.entity.UserEntity;
import com.project.ecommerce.user.respository.IUserRepository;
import com.project.ecommerce.user.respository.RolesRepository;
import com.project.ecommerce.user.service.IUserService;
import com.project.ecommerce.user.util.JwtUtil;
import com.project.ecommerce.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserImpl implements IUserService, UserDetailsService {

    public static final Logger logger = LoggerFactory.getLogger(UserImpl.class);
    private final IUserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final ModelMapper mapper = new ModelMapper();
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse createUser(AuthRegisterRequest authRegisterRequest) throws ApiException {
        logger.info("---El servidor ingresa al servicio para crear un usuario");
        String username = authRegisterRequest.username();
        String password = authRegisterRequest.password();
        List<String> roles = authRegisterRequest.authCreateRoleRequest().roleListName();
        Set<RoleEntity> roleList = new HashSet<>(rolesRepository.findRoleEntitiesByRoleEnumIn(roles));

        if (roleList.isEmpty()) {
            throw new IllegalArgumentException("Role no exist.");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .roles(roleList)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpire(true)
                .build();

        userRepository.saveAndFlush(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userEntity.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionEntities().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword(), authorities);

        String accessToken = jwtUtil.createToken(authentication);

        return new AuthResponse(userEntity.getUsername(), "successfully.", accessToken, true);
    }

    @Override
    public List<UserResponse> getAllUsers() throws ApiException {
        List<UserEntity> listUser;
        try {
            logger.info("---Entro al servicio para buscar usuarios registrados---");
            listUser = userRepository.findAll();
        } catch (Exception e) {
            logger.info("---Error al realizar metodo de traer todos los usuarios---");
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listUser.isEmpty()) {
            logger.info("---No hay usuarios registrados hasta el momento---");
            return Collections.emptyList();
        }
        logger.info("---No hay usuarios registrados---"); //esto no iria
        return listUser.stream()
                .map(userEntity -> mapper.map(userEntity, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getByDocument(String document) throws ApiException {
        try {
            logger.info("---Se conecto al servicio para buscar por documento un usuario---");
            Optional<UserEntity> userByDocument = userRepository.findByDocument(document);
            if (userByDocument.isPresent()) {
                logger.info("---Se encontro useario con documento {} en base de datos----", document);
                UserEntity user = userByDocument.get();
                return mapper.map(user, UserResponse.class);
            } else {
                logger.info("---El usuario con el documento {} no se encontro registrado", document);
                throw new ApiException("el usuario no se encuentra registrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.info("---Se produjo un erro al buscar usuario por documento---");
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse updateUser(long idUser, UserRequest userRequest) throws ApiException {
        try {
            logger.info("---Entro al servicio para actualizar usuario---");
            UserEntity user = userRepository.findByIdUser(idUser);
            if (user != null) {
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
                throw new ApiException("Usuario actualizado correctamente", HttpStatus.OK);
            } else {
                logger.info(("---El usuario no fue encontrado para actualizar---"));
                throw new ApiException("No se encontro el usuario para actualizar", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.info("---Se produjo un error al actualizar el usuario---");
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteUser(String document) throws ApiException {
        try {
            logger.info("---Entro al servicio para eliminar usuario---");
            Optional<UserEntity> userEntityOptional = userRepository.findByDocument(document);
            if (userEntityOptional.isPresent()) {
                UserEntity user = userEntityOptional.get();
                userRepository.delete(user);
                logger.info("---El usuario selecionado, fue eliminado correctamente---");
                throw new ApiException("usuario eliminado correctamente", HttpStatus.OK);
            } else {
                logger.info("---El usuario ya no existe----");
                throw new ApiException("El usuario no existe", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.info("---Se produjo un error al eliminar el usuario seleccionado---");
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe."));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionEntities().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isAccountNoLocked(),
                userEntity.isCredentialNoExpire(),
                authorities);
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtil.createToken(authentication);

        return new AuthResponse(username, "Successfully", accessToken, true);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
