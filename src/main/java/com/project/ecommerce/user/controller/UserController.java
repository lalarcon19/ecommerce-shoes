package com.project.ecommerce.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@PreAuthorize("denyAll()")
public class UserController {

    @GetMapping("/hello")
    @PreAuthorize("permitAll()")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/helloSecured")
    @PreAuthorize("hasAuthority('CREATE')")
    public String helloSecured() {
        return "Hello world secured.";
    }

    @GetMapping("/helloSecured2")
    @PreAuthorize("hasAuthority('READ')")
    public String helloSecured2() {
        return "Hello world secured.";
    }
}