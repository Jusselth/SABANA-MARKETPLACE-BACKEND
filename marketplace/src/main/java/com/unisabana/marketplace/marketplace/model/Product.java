package com.unisabana.marketplace.marketplace.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data // Esto genera Getters y Setters automáticos con Lombok
@AllArgsConstructor
@NoArgsConstructor

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

}