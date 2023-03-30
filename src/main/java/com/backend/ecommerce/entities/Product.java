package com.backend.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String productImageUrl;
    private String description;
    private Double price;
    private Integer stockQuantity;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime publishDate;
    @ManyToOne
    private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productImageUrl='" + productImageUrl + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + stockQuantity +
                ", publishDate=" + publishDate +
                '}';
    }
}
