package com.unisabana.marketplace.marketplace.model;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String title; 
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private String condition; 
    private String imageUrl;  
    private String ownerEmail; 

    public Product() {
        // Eliminamos el UUID para que el id sea manejado por el idGenerator del DataStore
    }
}