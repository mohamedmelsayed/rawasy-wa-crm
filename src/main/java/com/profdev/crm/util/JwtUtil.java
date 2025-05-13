package com.profdev.crm.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtUtil {
    private final String SECRET = "change_this_secret_to_a_long_random_string_for_production";
    private final long EXPIRATION_MS = 86400000; // 1 day
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public String getUsernameFromToken(String token) {
        return validateToken(token).getBody().getSubject();
    }

    public String getRoleFromToken(String token) {
        return (String) validateToken(token).getBody().get("role");
    }
}
