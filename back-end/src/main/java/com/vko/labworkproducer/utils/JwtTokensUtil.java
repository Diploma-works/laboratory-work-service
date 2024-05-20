package com.vko.labworkproducer.utils;

import com.vko.labworkproducer.dto.VerifyTokenResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokensUtil {

    @Value("${jwt.secret}")
    public String secret;

    @Value("${jwt.lifetime}")
    public Duration jwtLifeTime;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        var rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);
        var issuedDate = new Date();
        var expiredDate = new Date(issuedDate.getTime() + jwtLifeTime.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public VerifyTokenResponseDto verifyToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            long currentTimeMillis = System.currentTimeMillis();
            long expirationTimeMillis = claims.get("exp", Long.class) * 1000;
            if (expirationTimeMillis < currentTimeMillis) {
                return new VerifyTokenResponseDto(HttpStatus.UNAUTHORIZED, "Токен истек");
            } else {
                return new VerifyTokenResponseDto(HttpStatus.OK, "Токен актуален");
            }
        } catch (ExpiredJwtException ex) {
            return new VerifyTokenResponseDto(HttpStatus.UNAUTHORIZED, "Токен истек");
        } catch (Exception ex) {
            return new VerifyTokenResponseDto(HttpStatus.BAD_REQUEST, "Некорректный токен");
        }
    }
}
