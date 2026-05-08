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
        // Validación de seguridad: el email debe ser único en la memoria
        if (userRegistry.existsByEmail(user.getEmail())) {
            return "El usuario ya está registrado con ese correo.";
        }
        
        // Asignamos un ID único y guardamos
        user.setId(UUID.randomUUID().toString());
        userRegistry.save(user);
        return "Usuario registrado con éxito";
    }
    public String login(String email, String password) {
        User user = userRegistry.getUserByEmail(email);
    
        if (user == null) {
            return "Error: El usuario no existe.";
        }
    
        if (!user.getPassword().equals(password)) {
            return "Error: Contraseña incorrecta.";
        }
    
        return "Login exitoso";
    }
}