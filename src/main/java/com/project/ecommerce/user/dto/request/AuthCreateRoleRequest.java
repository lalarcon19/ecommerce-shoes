package com.project.ecommerce.user.dto.request;

import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthCreateRoleRequest(@Size(max = 3, message = "user cannot more than 3 roles") List<String> roleListName) {
}
