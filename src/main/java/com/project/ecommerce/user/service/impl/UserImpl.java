package com.project.ecommerce.user.service.impl;

import com.project.ecommerce.checkout.service.impl.CheckoutImpl;
import com.project.ecommerce.payments.entity.Payment;
import com.project.ecommerce.user.dto.request.AuthLoginRequest;
import com.project.ecommerce.user.dto.request.AuthRegisterRequest;
import com.project.ecommerce.user.dto.request.UserRequest;
import com.project.ecommerce.user.dto.response.AuthResponse;
import com.project.ecommerce.user.dto.response.UserResponse;
import com.project.ecommerce.user.entity.Role;
import com.project.ecommerce.user.entity.UserEntity;
import com.project.ecommerce.user.respository.UserRepository;
import com.project.ecommerce.user.respository.RolesRepository;
import com.project.ecommerce.user.service.UserService;
import com.project.ecommerce.user.util.JwtUtil;
import com.project.ecommerce.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
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

import static com.project.ecommerce.payments.service.impl.PaymentImpl.paymentToResponse;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserDetailsService, UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserImpl.class);
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
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

    @Override
    public AuthResponse login(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtil.createToken(authentication);

        return new AuthResponse(username, "Successfully", accessToken, true);
    }

    @Override
    public AuthResponse register(AuthRegisterRequest authRegisterRequest) {
        logger.info("---El servidor ingresa al servicio para crear un usuario");
        String username = authRegisterRequest.getUsername();
        String password = authRegisterRequest.getPassword();
        List<String> roles = authRegisterRequest.getRol().getRoles();
        Set<Role> roleList = new HashSet<>(rolesRepository.findRoleEntitiesByRoleEnumIn(roles));
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(username);

        if (user.isPresent()) {
            throw new ApiException("El usuario ya existe.", HttpStatus.BAD_REQUEST);
        }

        if (roleList.isEmpty()) {
            throw new ApiException("Role no exist.", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = UserEntity.builder()
                .name(authRegisterRequest.getName())
                .lastName(authRegisterRequest.getLastname())
                .email(authRegisterRequest.getEmail())
                .documentType(authRegisterRequest.getDocumentType())
                .document(authRegisterRequest.getDocument())
                .address(authRegisterRequest.getAddress())
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
    public List<UserResponse> getAllUsers() {
        List<UserEntity> listUser = userRepository.findAll();
        logger.info("---Entro al servicio para buscar usuarios registrados---");

        if (listUser.isEmpty()) {
            logger.info("---No hay usuarios registrados hasta el momento---");
            return Collections.emptyList();
        }

        return listUser.stream()
                .map(user -> {
                    if (user.getPayment() == null) {
                        user.setPayment(new Payment());
                    }
                    return userToResponse(user);
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getById(long id) {
        UserEntity user = userRepository.findById(id);

        if (user == null) {
            throw new ApiException("El usuario no existe", HttpStatus.BAD_GATEWAY);
        }

        if (user.getPayment() == null) {
            user.setPayment(new Payment());
        }

        return userToResponse(user);
    }

    @Override
    public void updateUser(long idUser, UserRequest userRequest) {
        logger.info("---Entro al servicio para actualizar usuario---");
        UserEntity user = userRepository.findById(idUser);

        if (user == null) {
            logger.info(("---El usuario no fue encontrado para actualizar---"));
            throw new ApiException("No se encontro el usuario para actualizar", HttpStatus.NOT_FOUND);
        }

        user.setName(userRequest.getName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setDocumentType(userRequest.getDocumentType());
        user.setDocument(userRequest.getDocument());
        user.setAddress(userRequest.getAddress());
        userRepository.saveAndFlush(user);

        logger.info("---El usuario fue actualizado correctamente---");
    }

    @Override
    public void deleteUser(long id) {
        logger.info("---Entro al servicio para eliminar usuario---");
        UserEntity user = userRepository.findById(id);

        if (user == null)  {
            logger.info("---El usuario no existe----");
            throw new ApiException("El usuario no existe", HttpStatus.NOT_FOUND);
        }

        userRepository.delete(user);
        logger.info("---El usuario selecionado, fue eliminado correctamente---");
    }


    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public static UserResponse userToResponse(UserEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .documentType(entity.getDocumentType())
                .document(entity.getDocument())
                .address(entity.getAddress())
                .checkouts(entity.getCheckout().stream()
                        .map(CheckoutImpl::checkoutToResponse)
                        .collect(Collectors.toList()))
                .payment(paymentToResponse(entity.getPayment()))
                .build();
    }

    public static UserEntity userToResponse(UserResponse response) {
        return UserEntity.builder()
                .id(response.getId())
                .name(response.getName())
                .lastName(response.getLastName())
                .email(response.getEmail())
                .documentType(response.getDocumentType())
                .document(response.getDocument())
                .address(response.getAddress())
                .build();
    }
}
