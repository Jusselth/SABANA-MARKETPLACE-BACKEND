package com.unisabana.marketplace.marketplace.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unisabana.marketplace.marketplace.model.Order;
import com.unisabana.marketplace.marketplace.model.OrderItem;
import com.unisabana.marketplace.marketplace.model.Product;
import com.unisabana.marketplace.marketplace.repository.DataStore;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = {"https://sabana-marketplace-frontend.vercel.app", "http://localhost:5173", "http://localhost:5175"})
public class OrderController {

    private final DataStore dataStore;

    public OrderController(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        // 1. Validar datos básicos
        if (order.getEmail() == null || order.getEmail().trim().isEmpty()) {
            return new ResponseEntity<>("El email/contacto es obligatorio", HttpStatus.BAD_REQUEST);
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            return new ResponseEntity<>("El carrito no puede estar vacío", HttpStatus.BAD_REQUEST);
        }

        // 2. Validar el Stock en el DataStore para cada producto comprado
        for (OrderItem item : order.getItems()) {
            if (item.getProductId() == null) {
                return new ResponseEntity<>("Hay un producto en el carrito sin ID válido", HttpStatus.BAD_REQUEST);
            }
            
            // Pasamos el ID directamente evitando el desempaquetado manual
            Product product = dataStore.findById(item.getProductId()).orElse(null);
            
            if (product == null) {
                return new ResponseEntity<>("El producto con ID " + item.getProductId() + " no existe.", HttpStatus.NOT_FOUND);
            }
            
            if (product.getStock() < item.getQuantity()) {
                return new ResponseEntity<>("Stock insuficiente para: " + product.getTitle() + ". Disponible: " + product.getStock(), HttpStatus.BAD_REQUEST);
            }
        }

        // 3. Si todo está correcto, restamos el stock real del inventario
        for (OrderItem item : order.getItems()) {
            dataStore.findById(item.getProductId()).ifPresent(product -> {
                product.setStock(product.getStock() - item.getQuantity());
                dataStore.update(product); // Sincroniza el cambio en memoria en el DataStore
            });
        }

        // 4. Asignar ID a la orden y guardarla
        order.setId(dataStore.generateNextOrderId());
        dataStore.getOrderList().add(order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}