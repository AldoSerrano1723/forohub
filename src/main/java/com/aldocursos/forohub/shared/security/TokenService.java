package com.aldocursos.forohub.shared.security;

import com.aldocursos.forohub.modules.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String jwtSecret;
    @Value("${api.security.expiration}")
    private Long jwtExpiration;

    public String generarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withIssuer("forohub")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(fechaExpiracion())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al generar token", exception);
        }
    }

    private Instant fechaExpiracion() {
        return LocalDateTime.now().plusHours(jwtExpiration).toInstant(ZoneOffset.UTC);
    }
}