package com.dep.depApp.Service;

import com.dep.depApp.entity.Department;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    @Value(
            "${jwt.secretKey}"
    )
    private String jwtSecretKey;

    private SecretKey getSecreetKey()
    {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(Department department)
    {
        return Jwts.builder()
                .subject(department.getId().toString())
                .claim("name",department.getName())
                .claim("roles", Set.of("ADMIN","ROLE"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60))
                .signWith(getSecreetKey())
                .compact();
    }

    public Long getUserIdFromToken(String token)
    {
        Claims claims=Jwts.parser()
                .verifyWith(getSecreetKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }
}
