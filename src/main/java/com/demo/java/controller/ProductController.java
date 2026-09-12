package com.demo.java.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.java.entity.Product;
import com.demo.java.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

        private final ProductService productService;

        public ProductController(ProductService productService) {
                this.productService = productService;
        }

        @PostMapping
        @PreAuthorize("hasRole('SELLER')")
        public ResponseEntity<Product> createProduct(
                        @RequestBody Product product) {

                return ResponseEntity.ok(
                                productService.saveProduct(product));
        }

        @GetMapping
        @PreAuthorize("hasAnyRole('CUSTOMER')")
        public ResponseEntity<List<Product>> getAllProducts() {

                return ResponseEntity.ok(
                                productService.getAllProducts());
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN','SELLER','CUSTOMER')")
        public ResponseEntity<Product> getProductById(
                        @PathVariable Long id) {

                return productService.getProductById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/sku/{sku}")
        @PreAuthorize("hasAnyRole('ADMIN','SELLER','CUSTOMER')")
        public ResponseEntity<Product> getProductBySku(
                        @PathVariable String sku) {

                return productService.getProductBySku(sku)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/category/{category}")
        @PreAuthorize("hasAnyRole('CUSTOMER')")
        public ResponseEntity<List<Product>> getByCategory(
                        @PathVariable String category) {

                return ResponseEntity.ok(
                                productService.getProductsByCategory(category));
        }

        @GetMapping("/brand/{brand}")
        @PreAuthorize("hasAnyRole('ADMIN','SELLER','CUSTOMER')")
        public ResponseEntity<List<Product>> getByBrand(
                        @PathVariable String brand) {

                return ResponseEntity.ok(
                                productService.getProductsByBrand(brand));
        }

        @GetMapping("/my-products")
        @PreAuthorize("hasRole('SELLER')")
        public ResponseEntity<List<Product>> getMyProducts() {

                return ResponseEntity.ok(
                                productService.getMyProducts());
        }

        @PutMapping("/{id}")
        public ResponseEntity<Product> updateProduct(
                        @PathVariable Long id,
                        @RequestBody Product product) {

                return ResponseEntity.ok(
                                productService.updateProduct(id, product));
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('SELLER')")
        public ResponseEntity<String> deleteProduct(
                        @PathVariable Long id) {

                productService.deleteProduct(id);

                return ResponseEntity.ok(
                                "Product Deleted Successfully");
        }
}