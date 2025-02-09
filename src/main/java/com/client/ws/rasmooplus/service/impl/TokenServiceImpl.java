package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.model.UserCredentials;
import com.client.ws.rasmooplus.service.TokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecretKeyBuilder;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${webservices.rasplus.jwt.expiration}")
    private String expiration;

    @Value("${webservices.rasplus.jwt.secret}")
    private String secretKeyValue;

    @Override
    public String getToken(Authentication auth) {
        UserCredentials user = (UserCredentials) auth.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        Key secretKey = Keys.hmacShaKeyFor(secretKeyValue.getBytes());

        return Jwts.builder()
                .issuer("API Rasmoo Plus")
                .subject(user.getId().toString())
                .issuedAt(today)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public Boolean isValid(String token) {
        try {
            // if not throw an exception, means that token is valid
            getTokenClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Long getUserId(String token) {
        Claims claims = getTokenClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    private Claims getTokenClaims(String token) {
        JwtParser jwtParser = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKeyValue.getBytes())).build();
        return jwtParser.parseSignedClaims(token).getPayload();
    }
}
