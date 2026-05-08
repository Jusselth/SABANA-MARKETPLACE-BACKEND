package com.unisabana.marketplace.marketplace.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private String ownerEmail; 

    public Product() {
        this.id = UUID.randomUUID().toString();
    }
}