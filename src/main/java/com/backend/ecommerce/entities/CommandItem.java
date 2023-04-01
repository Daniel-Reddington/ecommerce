package com.backend.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Positive(message = "command item price should be positive and more than zero")
    private Double price;
    @Positive(message = "command item quantity should be positive and more than zero")
    private Integer quantity;
    @ManyToOne
    @JsonIgnore
    private Command command;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product = new Product();
    @Override
    public String toString() {
        return "CommandItem{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", product=" + product +
                '}';
    }

}
