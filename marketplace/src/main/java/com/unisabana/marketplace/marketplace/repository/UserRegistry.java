package com.unisabana.marketplace.marketplace.repository;

import com.unisabana.marketplace.marketplace.model.User;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;

@Repository
public class UserRegistry {
    // Nuestra "BD" en memoria. La llave es el email (debe ser único).
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    @jakarta.annotation.PostConstruct
    public void initAdmin() {
        User admin = new User();
        admin.setId("ADMIN-001");
        admin.setName("Jusselth");
        admin.setLastName("Chica"); 
        admin.setEmail("jusselth@unisabana.edu.co");
        admin.setPassword("admin123");
        admin.setCareer("Ingeniería de Sistemas");
        admin.setRole("ADMIN");
        
        users.put(admin.getEmail(), admin);
    }

    public User save(User user) {
        users.put(user.getEmail(), user);
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    public boolean existsByEmail(String email) {
        return users.containsKey(email);
    }
    public User getUserByEmail(String email) {
        return users.get(email);
    }
    public java.util.List<User> findAll() {
    return new java.util.ArrayList<>(users.values());
    }
    public boolean deleteByEmail(String email) {
    return users.remove(email) != null;
    }
}