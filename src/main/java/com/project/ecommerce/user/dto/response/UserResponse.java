package com.project.ecommerce.user.dto.response;

import com.project.ecommerce.user.util.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {

    private long id;

    private String name;

    private String lastName;

    private String email;

    private DocumentType documentType;

    private String document;

    private String address;
}
