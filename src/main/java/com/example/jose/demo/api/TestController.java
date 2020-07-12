package com.example.jose.demo.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TestController {

    @GetMapping(value = "/me")
    public Authentication get(Authentication authentication) {

        System.out.println("principal name is " + authentication.getName());
        return authentication;
    }
}
