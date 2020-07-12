package com.example.jose.demo.config;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.singletonMap;

public class JwtHelper {

    private JwtHelper() {
    }

    public static String generateJwt(String name, Set<String> roles) {
        JWTClaimsSet.Builder builder = defaultClaims(name).claim("realm_access", singletonMap("roles", roles));
        return "bearer ".concat(new PlainJWT(builder.build()).serialize());
    }

    private static JWTClaimsSet.Builder defaultClaims(String name) {
        return new JWTClaimsSet.Builder()
                .issuer("http://localhost:8080/auth/realms/wrongrealm")
                .subject(UUID.randomUUID().toString())
                .claim("preferred_username", name)
                .expirationTime(Date.from(ZonedDateTime.now().plusDays(1).toInstant()));
    }
}
