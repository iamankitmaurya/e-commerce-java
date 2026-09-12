package com.demo.java.service;

import java.util.List;
import java.util.Optional;

import com.demo.java.entity.Product;

public interface ProductService {

    Product saveProduct(Product product);

    Product updateProduct(Long id, Product product);

    Optional<Product> getProductById(Long id);

    List<Product> getAllProducts();

    void deleteProduct(Long id);

    Optional<Product> getProductBySku(String sku);

    Optional<Product> getProductByBarcode(String barcode);

    List<Product> getProductsByUser(Long userId);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);
}