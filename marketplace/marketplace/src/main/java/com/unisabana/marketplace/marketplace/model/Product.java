package com.unisabana.marketplace.marketplace.model;

import java.util.UUID;
import lombok.Data;
@Data
public class Product {
    private String id;
    private String title; // Cambiado de 'name' a 'title'
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private String condition; // Agregado
    private String imageUrl;  // Agregado
    private String ownerEmail; 

    public Product() {
        this.id = UUID.randomUUID().toString();
    }
}