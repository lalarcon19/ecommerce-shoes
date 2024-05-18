package com.project.ecommerce.user.dto.request;

import com.project.ecommerce.user.util.DocumentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRegisterRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotBlank
    private String email;
    private DocumentType documentType;
    private String document;
    private String address;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Valid
    private AuthCreateRoleRequest rol;

}
