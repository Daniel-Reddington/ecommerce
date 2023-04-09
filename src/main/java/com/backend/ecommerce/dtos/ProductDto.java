package com.backend.ecommerce.dtos;

import com.backend.ecommerce.entities.Category;
import com.backend.ecommerce.validator.AddMethodValidator;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    @NotBlank(message = "product name is required", groups = AddMethodValidator.class)
    @Size(max = 70, message = "product name max length should be {max}")
    private String productName;
    private MultipartFile productImage;
    private String description;
    @Positive(message = "product price should be positive and more than zero")
    private Double price;
    @PositiveOrZero(message = "Stock quantity must be positive")
    private Integer stockQuantity;
    private Category category;
}
