package com.aldocursos.forohub.modules.usuario;

import com.aldocursos.forohub.shared.security.DatosTokenJWT;
import com.aldocursos.forohub.shared.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity iniciarSesion(@RequestBody @Valid DatosAutenticacion datos){
        var autenticacionToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.password());
        var autenticacion = manager.authenticate(autenticacionToken);
        var usuario = (Usuario) autenticacion.getPrincipal();
        var tokenJWT = tokenService.generarToken(usuario);
        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }

}

