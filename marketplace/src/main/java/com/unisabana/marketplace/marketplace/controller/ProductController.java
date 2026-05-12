package com.unisabana.marketplace.marketplace.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unisabana.marketplace.marketplace.model.Product;
import com.unisabana.marketplace.marketplace.repository.DataStore;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = {"https://sabana-marketplace-frontend.vercel.app", "http://localhost:5175/"}) 
public class ProductController {

    private final DataStore dataStore;

    public ProductController(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return dataStore.getProductList();
    }

    @GetMapping("/owner/{email}")
    public List<Product> getProductsByOwner(@PathVariable String email) {
        return dataStore.getProductList().stream()
                .filter(p -> p.getOwnerEmail() != null && p.getOwnerEmail().equalsIgnoreCase(email))
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>("El título es obligatorio", HttpStatus.BAD_REQUEST);
        }
        
        // Asignamos ID manualmente ya que no hay base de datos real
        product.setId(dataStore.generateNextId());
        if (product.getStock() == null) product.setStock(1);

        dataStore.getProductList().add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return dataStore.findById(id)
                .map(product -> {
                    product.setTitle(productDetails.getTitle());
                    product.setPrice(productDetails.getPrice());
                    product.setStock(productDetails.getStock());
                    product.setCategory(productDetails.getCategory());
                    product.setDescription(productDetails.getDescription());
                    product.setImageUrl(productDetails.getImageUrl());
                    product.setCondition(productDetails.getCondition());
                    
                    dataStore.update(product);
                    return ResponseEntity.ok(product);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean removed = dataStore.getProductList().removeIf(p -> p.getId().equals(id));
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
