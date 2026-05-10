package com.unisabana.marketplace.marketplace.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unisabana.marketplace.marketplace.model.Product;
import com.unisabana.marketplace.marketplace.repository.DataStore;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*") 
public class ProductController {

    private final DataStore dataStore;

    public ProductController(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    // --- NUEVO MÉTODO: Para que PersonalInventory pueda ver los productos ---
    // 1. Para PublicShowcase: Devuelve TODO (lo de todos los usuarios)
    @GetMapping
    public List<Product> getAllProducts() {
        return dataStore.getProductList();
    }

    // 2. Para PersonalInventory: Devuelve solo lo del usuario logueado
    // Se usa así: /api/v1/products/owner/usuario@unisabana.edu.co
    @GetMapping("/owner/{email}")
    public List<Product> getProductsByOwner(@PathVariable String email) {
        return dataStore.getProductList().stream()
                .filter(p -> p.getOwnerEmail() != null && p.getOwnerEmail().equalsIgnoreCase(email))
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        // Ajustamos la validación a 'title'
        if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>("El título es obligatorio", HttpStatus.BAD_REQUEST);
        }
        
        // El stock por defecto es 1 si no se envía
        if (product.getStock() == null) product.setStock(1);

        dataStore.getProductList().add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        boolean removed = dataStore.getProductList().removeIf(p -> p.getId().equals(id));
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}