package com.example.jose.demo.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.text.ParseException;
import java.util.Map;

@TestConfiguration
public class PlainJWTSecurityConfig {

    @Primary
    @Bean
    public JwtDecoder plainJwtDecoder() {
        final SecurityConfig.UsernameSubClaimAdapter claimAdapter = new SecurityConfig.UsernameSubClaimAdapter();
        return token -> {
            try {

                final JWT jwt = JWTParser.parse(token);
                final Map<String, Object> claims = claimAdapter.convert(jwt.getJWTClaimsSet().getClaims());
                return Jwt.withTokenValue(token)
                        .headers((h) -> h.putAll(jwt.getHeader().toJSONObject()))
                        .claims((c) -> c.putAll(claims)).build();
            } catch (ParseException e) {
                throw new JwtException(e.getMessage(), e);
            }
        };
    }
}
