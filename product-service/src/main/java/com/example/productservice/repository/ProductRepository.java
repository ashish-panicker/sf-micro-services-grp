package com.example.productservice.repository;

import com.example.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String q, Pageable pageable);

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    List<Product> findBySkuIn(List<String> skus);
}
