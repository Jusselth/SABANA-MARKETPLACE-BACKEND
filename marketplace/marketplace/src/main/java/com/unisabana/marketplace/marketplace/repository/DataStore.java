package com.unisabana.marketplace.marketplace.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import com.unisabana.marketplace.marketplace.model.Product;

import jakarta.annotation.PostConstruct;

@Component
public class DataStore {
    // La fuente de verdad para el Ticket 2.1 y 2.2
    private final List<Product> productList = new CopyOnWriteArrayList<>();

    public List<Product> getProductList() {
        return productList;
    }

    @PostConstruct
    public void initData() {
 
        Product p1 = new Product();
        p1.setName("Cargador tipo C");
        p1.setPrice(36000.0);
        p1.setStock(2);
        p1.setCategory("Electrónica");
        p1.setOwnerEmail("jusselth@unisabana.edu.co");
        productList.add(p1);
    }
}