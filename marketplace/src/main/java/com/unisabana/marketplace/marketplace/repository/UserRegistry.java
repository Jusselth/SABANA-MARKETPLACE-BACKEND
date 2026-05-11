package com.unisabana.marketplace.marketplace.repository;

import com.unisabana.marketplace.marketplace.model.User;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;

@Repository
public class UserRegistry {
    // Nuestra "BD" en memoria. La llave es el email (debe ser único).
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

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
}