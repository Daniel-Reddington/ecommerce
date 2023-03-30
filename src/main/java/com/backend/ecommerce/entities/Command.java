package com.backend.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
public class Command {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double totalPrice;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime commandDate;
    @OneToMany(mappedBy = "command", cascade = CascadeType.ALL)
    private List<CommandItem> commandItems;


}
