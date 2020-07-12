package com.example.jose.demo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class TestController {

    @GetMapping(value = "/me")
    public Jwt get(final @AuthenticationPrincipal Jwt authentication) {
        log.info("user name is {}",authentication.getClaimAsString("preferred_username"));
        return authentication;
    }
}
