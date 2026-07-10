package com.dep.depApp.Service;

import com.dep.depApp.entity.Department;
import com.dep.depApp.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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

    public String generateToken(User user)
    {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("name",user.getEmail())
                //.claim("roles", Set.of("ADMIN","ROLE"))
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
