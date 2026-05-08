package com.unisabana.marketplace.marketplace.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            return new ResponseEntity<>("El nombre es obligatorio", HttpStatus.BAD_REQUEST);
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            return new ResponseEntity<>("El precio debe ser mayor a 0", HttpStatus.BAD_REQUEST);
        }
        if (product.getStock() == null || product.getStock() < 0) {
            return new ResponseEntity<>("El stock no puede ser negativo", HttpStatus.BAD_REQUEST);
        }

        if (product.getOwnerEmail() == null) {
            product.setOwnerEmail("usuario.prueba@unisabana.edu.co");
        }

        dataStore.getProductList().add(product);
        
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        boolean removed = dataStore.getProductList().removeIf(p -> p.getId().equals(id));
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}