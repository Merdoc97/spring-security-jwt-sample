package com.example.jose.demo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class TestController {

    @GetMapping(value = "/me")
    public Authentication get(Authentication authentication) {

        log.info("user name is {}", authentication.getName());

        return authentication;
    }
}
