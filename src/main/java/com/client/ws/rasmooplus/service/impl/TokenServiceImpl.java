package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.model.UserCredentials;
import com.client.ws.rasmooplus.service.TokenService;
import io.jsonwebtoken.*;
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

    private static final Key SECRET_KEY = Jwts.SIG.HS256.key().build();

    @Override
    public String getToken(Authentication auth) {
        UserCredentials user = (UserCredentials) auth.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .issuer("API Rasmoo Plus")
                .subject(user.getId().toString())
                .issuedAt(today)
                .expiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    @Override
    public Boolean isValid(String token) {
        try {
            Claims claims = getTokenClaims(token);
            return !claims.isEmpty();
        } catch(ExpiredJwtException e) {
            System.out.println("Erro: Token expirado!");
        } catch (SignatureException e) {
            System.out.println("Erro: Assinatura inválida!");
        } catch (MalformedJwtException e) {
            System.out.println("Erro: Token malformado!");
        } catch (Exception e) {
            System.out.println("Erro: Token inválido! " + e.getMessage());
        }
        return false;
    }

    @Override
    public Long getUserId(String token) {
        Claims claims = getTokenClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    private Claims getTokenClaims(String token) {
        JwtParser jwtParser = Jwts.parser().verifyWith((SecretKey) SECRET_KEY).build();
        return jwtParser.parseSignedClaims(token).getPayload();
    }
}
