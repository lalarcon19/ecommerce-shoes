package com.project.ecommerce.user.dto.request;

import com.project.ecommerce.user.util.DocumentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String name;

    private String lastName;

    private String email;

    private DocumentType documentType;

    private String document;

    private String address;

}
