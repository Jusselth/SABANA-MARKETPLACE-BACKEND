package com.unisabana.marketplace.marketplace.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unisabana.marketplace.marketplace.model.User;
import com.unisabana.marketplace.marketplace.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://sabana-marketpalce-frontend-buafcup44-juanmora-uxs-projects.vercel.app/home,http://localhost:5175") //  React pueda conectarse
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