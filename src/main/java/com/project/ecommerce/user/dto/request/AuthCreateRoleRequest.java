package com.project.ecommerce.user.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthCreateRoleRequest {
    @Size(max = 3, message = "user cannot more than 3 roles")
    private List<String> roleListName;
}
