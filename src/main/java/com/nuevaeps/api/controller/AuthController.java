package com.nuevaeps.api.controller;

import com.nuevaeps.api.dto.JwtResponse;
import com.nuevaeps.api.dto.LoginRequest;
import com.nuevaeps.api.dto.RegisterRequest;
import com.nuevaeps.api.model.Usuario;
import com.nuevaeps.api.security.JwtUtils;
import com.nuevaeps.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación y registro de usuarios")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autenticar usuario y obtener token JWT")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());
            Usuario usuario = usuarioService.obtenerPorUsername(loginRequest.getUsername()).orElse(null);

            if (usuario != null) {
                return ResponseEntity.ok(new JwtResponse(jwt, usuario.getId(), usuario.getUsername()));
            }
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Credenciales inválidas");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Registro", description = "Registrar nuevo usuario")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(
                    registerRequest.getUsername(),
                    registerRequest.getPassword()
            );
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar usuario: " + e.getMessage());
        }
    }
}
