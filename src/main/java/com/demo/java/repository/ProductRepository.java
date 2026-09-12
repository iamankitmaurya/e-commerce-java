package com.demo.java.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.java.entity.Product;
import com.demo.java.entity.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    Optional<Product> findByBarcode(String barcode);

    List<Product> findByUser(User user);

    List<Product> findByCategory(String category);

    List<Product> findByBrand(String brand);
}