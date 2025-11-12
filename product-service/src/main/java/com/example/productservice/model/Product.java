package com.example.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;


    @Column(length = 1000)
    private String description;


    @Column(nullable = false, unique = true, name = "sku_code")
    private String sku;


    @Column(nullable = false)
    private double price;


    @Column(nullable = false, name = "available_quantity")
    private Integer quantity;


    @Column(nullable = false)
    private boolean available = true;


    public Product(String name, String description, String sku, double price, Integer quantity) {
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.available = quantity > 0;
    }
}
