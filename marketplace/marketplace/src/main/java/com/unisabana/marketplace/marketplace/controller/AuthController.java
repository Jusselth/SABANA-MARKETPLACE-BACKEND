package com.unisabana.marketplace.marketplace.controller;

import com.unisabana.marketplace.marketplace.model.User;
import com.unisabana.marketplace.marketplace.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5175") //  React pueda conectarse
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
    public String login(@RequestBody User loginData) {
        // Usamos el objeto User para recibir email y password de forma fácil
        return authService.login(loginData.getEmail(), loginData.getPassword());
}
}