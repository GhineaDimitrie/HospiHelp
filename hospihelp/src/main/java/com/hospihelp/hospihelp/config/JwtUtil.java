package com.hospihelp.hospihelp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String genereazaToken(UserDetails userDetails, String rol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol);
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extrageEmail(String token) {
        return extrageClaims(token).getSubject();
    }

    public String extrageRol(String token) {
        return extrageClaims(token).get("rol", String.class);
    }

    public boolean esteTokenValid(String token, UserDetails userDetails) {
        final String email = extrageEmail(token);
        return email.equals(userDetails.getUsername()) && !esteTokenExpirat(token);
    }

    private boolean esteTokenExpirat(String token) {
        return extrageClaims(token).getExpiration().before(new Date());
    }

    private Claims extrageClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}