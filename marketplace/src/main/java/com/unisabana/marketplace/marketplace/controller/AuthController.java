package com.unisabana.marketplace.marketplace.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unisabana.marketplace.marketplace.model.User;
import com.unisabana.marketplace.marketplace.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {"https://sabana-marketplace-frontend.vercel.app", "http://localhost:5175/"})
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        // Llamamos al servicio que devuelve un User
        User user = authService.login(loginData.getEmail(), loginData.getPassword());
        
        if (user != null) {
            // Enviamos el objeto completo (ID, Nombre, Email, ROL, etc.)
            return ResponseEntity.ok(user);
        } else {
            // Si el servicio devolvió null, las credenciales están mal
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }
    }
}