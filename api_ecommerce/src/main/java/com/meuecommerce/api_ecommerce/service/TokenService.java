package com.meuecommerce.api_ecommerce.service;

import com.meuecommerce.api_ecommerce.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String gerarToken(Usuario usuario) {
        Date hoje = new Date();

        Date dataExpiracao = new Date(hoje.getTime() + 7200000);

        return Jwts.builder()
                .setIssuer("api-ecommerce") 
                .setSubject(usuario.getEmail()) 
                .setIssuedAt(hoje) 
                .setExpiration(dataExpiracao) 
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) 
                .compact();
    }

    public String validarToken(String tokenJWT) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(tokenJWT)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {

            return null;
        }
    }
}
