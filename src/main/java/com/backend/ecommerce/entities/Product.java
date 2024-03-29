package com.backend.ecommerce.entities;

import com.backend.ecommerce.validator.AddMethodValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 70, nullable = false)
    @NotBlank(message = "product name is required", groups = AddMethodValidator.class)
    @Size(max = 70, message = "product name max length should be {max}")
    private String productName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String productImageUrl;

    private String description;

    @Positive(message = "product price should be positive and more than zero")
    private Double price;

    @PositiveOrZero(message = "Stock quantity must be positive")
    private Integer stockQuantity;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime publishDate;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CommandItem> commandItems;

    @ManyToOne
    private AppUser appUser;


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
