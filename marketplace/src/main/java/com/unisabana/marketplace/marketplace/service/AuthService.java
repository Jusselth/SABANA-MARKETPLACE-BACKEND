package com.unisabana.marketplace.marketplace.service;

import com.unisabana.marketplace.marketplace.model.User;
import com.unisabana.marketplace.marketplace.repository.UserRegistry;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRegistry userRegistry;

    public AuthService(UserRegistry userRegistry) {
        this.userRegistry = userRegistry;
    }

    public String register(User user) {
        if (userRegistry.existsByEmail(user.getEmail())) {
            return "El usuario ya está registrado con ese correo.";
        }
        
        user.setId(UUID.randomUUID().toString());
        
        // Si el rol viene vacío, le ponemos USER por defecto
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        
        userRegistry.save(user);
        return "Usuario registrado con éxito";
    }

    //devuelve el objeto User
    public User login(String email, String password) {
        User user = userRegistry.getUserByEmail(email);
    
        // Validamos que el usuario exista y que la contraseña sea igual
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
    
        return null; // Si falla, devolvemos null
    }
}