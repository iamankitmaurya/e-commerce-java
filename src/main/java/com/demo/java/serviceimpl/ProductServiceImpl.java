package com.demo.java.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.java.entity.Product;
import com.demo.java.entity.User;
import com.demo.java.repository.ProductRepository;
import com.demo.java.repository.UserRepository;
import com.demo.java.service.JwtService;
import com.demo.java.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public Product saveProduct(Product product) {
        Long userId = jwtService.getCurrentUserId();
        User seller = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        product.setUser(seller);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Long userId = jwtService.getCurrentUserId();

        Product existingProduct = productRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setCategory(product.getCategory());

        return productRepository.save(existingProduct);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        Long currentSellerId = jwtService.getCurrentUserId();

        if (!product.getUser().getId().equals(currentSellerId)) {

            throw new RuntimeException(
                    "You can delete only your own product");
        }

        productRepository.delete(product);
    }

    @Override
    public Optional<Product> getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    @Override
    public Optional<Product> getProductByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }

    @Override
    public List<Product> getProductsByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        return productRepository.findByUser(user);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getMyProducts() {
        Long userId = jwtService.getCurrentUserId();
        return productRepository
                .findByUserId(7L);
    }
}