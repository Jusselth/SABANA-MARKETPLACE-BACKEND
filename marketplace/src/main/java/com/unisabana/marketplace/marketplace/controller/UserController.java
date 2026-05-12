package com.unisabana.marketplace.marketplace.controller;

import java.util.List;
import java.util.Optional; 
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus; 
import org.springframework.web.bind.annotation.*;

import com.unisabana.marketplace.marketplace.model.User;
import com.unisabana.marketplace.marketplace.repository.UserRegistry;
import com.unisabana.marketplace.marketplace.repository.DataStore;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = {"https://sabanamarketplace-frontend.vercel.app", "http://localhost:5175/"})
public class UserController {

    private final UserRegistry userRegistry;
    private final DataStore dataStore;

    public UserController(UserRegistry userRegistry, DataStore dataStore) {
        this.userRegistry = userRegistry;
        this.dataStore = dataStore;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRegistry.findAll();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) { 
        if (email.equalsIgnoreCase("jusselth@unisabana.edu.co")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("¡Error! No puedes eliminar al administrador principal del sistema.");
        }
        
        dataStore.deleteProductsByOwner(email);
        boolean deleted = userRegistry.deleteByEmail(email);
        
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    
    @PatchMapping("/{email}/toggle-role")
    public ResponseEntity<?> toggleUserRole(@PathVariable String email) {
        // 1. Protección: Nadie puede quitarle el admin a Jusselth
        if (email.equalsIgnoreCase("jusselth@unisabana.edu.co")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("El administrador principal no puede ser degradado.");
        }

        Optional<User> userOpt = userRegistry.findByEmail(email);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // 2. Lógica de Toggle (Si es ADMIN pasa a USER, y viceversa)
            if ("ADMIN".equals(user.getRole())) {
                user.setRole("USER");
            } else {
                user.setRole("ADMIN");
            }
            
            userRegistry.save(user);
            return ResponseEntity.ok(user); // Devolvemos el usuario actualizado
        }
        
        return ResponseEntity.notFound().build();
    }
}