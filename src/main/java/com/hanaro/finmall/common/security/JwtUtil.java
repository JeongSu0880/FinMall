package com.hanaro.finmall.common.security;

import com.hanaro.finmall.common.security.exception.CustomJwtException;
import com.hanaro.finmall.user.UserRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String JwtSecret) {
        secretKey = Keys.hmacShaKeyFor(JwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public Map<String, Object> validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new CustomJwtException("Token Expired");
        } catch (JwtException e) {
            throw new CustomJwtException("Jwt Error");
        } catch (Exception e) {
            throw new CustomJwtException("Error: " + e.getMessage());
        }
    }

    public Map<String, Object> authenticationToClaims(Authentication authentication) {
        UserAuthDTO dto = (UserAuthDTO) authentication.getPrincipal();

        if (dto == null) {
            throw new CustomJwtException("Invalid Authentication");
        }

        Map<String, Object> claims = Map.of(
                "id", dto.getId(),
                "username", dto.getUsername(),
                "role", dto.getRole().name()
        );

        return claims;
    }

    public Authentication getAuthentication(Map<String, Object> claims) {

        UserAuthDTO user = UserAuthDTO.builder()
                .id(((Number) claims.get("id")).longValue())
                .username((String) claims.get("username"))
                .role(UserRole.valueOf((String) claims.get("role")))
                .build();

        return new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
    }

    public String generateToken(Map<String, Object> valueMap, int min) {
        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .claims().add(valueMap).and()
                .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(secretKey)
                .compact();
    }
}