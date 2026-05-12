package com.unisabana.marketplace.marketplace.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;
import com.unisabana.marketplace.marketplace.model.Product;
import jakarta.annotation.PostConstruct;

@Component
public class DataStore {
    private final List<Product> productList = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> getProductList() {
        return productList;
    }

    public Optional<Product> findById(Long id) {
        return productList.stream()
                .filter(p -> p.getId() != null && p.getId().equals(id))
                .findFirst();
    }

    public Product update(Product updatedProduct) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(updatedProduct.getId())) {
                productList.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }

    public Long generateNextId() {
        return idGenerator.getAndIncrement();
    }

    @PostConstruct
    public void initData() {
        Product p1 = new Product();
        p1.setId(generateNextId());
        p1.setTitle("Cargador tipo C");
        p1.setPrice(36000.0);
        p1.setStock(1);
        p1.setCategory("Electrónica");
        p1.setOwnerEmail("prueba@unisabana.edu.co");
        p1.setDescription("Cargador de carga rápida para dispositivos Android.");
        productList.add(p1);
    }
    public void deleteProductsByOwner(String email) {
        // Removemos todos los productos cuyo ownerEmail coincida
        productList.removeIf(p -> p.getOwnerEmail() != null && p.getOwnerEmail().equalsIgnoreCase(email));
    }
}