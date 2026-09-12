package com.demo.java.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.demo.java.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "products")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @Column(length = 5000)
    private String description;

    private String shortDescription;

    private String brand;

    private String model;

    private String manufacturer;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private BigDecimal discountPrice;

    private Integer discountPercentage;

    private Integer stock;

    private Integer minimumStock;

    private Integer soldQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(unique = true)
    private String sku;

    @Column(unique = true)
    private String barcode;

    private String thumbnailImage;

    private String imageUrl1;

    private String imageUrl2;

    private String imageUrl3;

    private String imageUrl4;

    private String videoUrl;

    private String category;

    private String subCategory;

    private String tags;

    private Double weight;

    private Double length;

    private Double width;

    private Double height;

    private String color;

    private String size;

    private String material;

    private Double averageRating;

    private Integer totalRatings;

    private Integer totalReviews;

    private Boolean freeShipping;

    private BigDecimal shippingCharge;

    private Integer estimatedDeliveryDays;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private String metaTitle;

    private String metaDescription;

    private String slug;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Long createdBy;

    private Long updatedBy;

    private Boolean deleted;

    private LocalDateTime deletedAt;
}