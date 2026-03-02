package com.aldocursos.forohub.shared.security;

import com.aldocursos.forohub.modules.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        // Si se encuentra un token JWT, valida y extrae la información del usuario
        if (tokenJWT != null){
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = usuarioRepository.findByEmail(subject);
            // Crea un objeto de autenticación con los detalles del usuario
            var authenrtication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            // Guarda la autenticación en el contexto de seguridad de Spring
            SecurityContextHolder.getContext().setAuthentication(authenrtication);
        }

        // Si el token es válido, continúa con la cadena de filtros
        filterChain.doFilter(request,response);
    }

    // Extrae el token JWT del encabezado de autorización
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
