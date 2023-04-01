package com.backend.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class AppUser {

    @Id
    private String id;
    @Email(message = "email must be a type email")
    private String email;
    @Column(length = 60)
    private String firstName;
    @Column(length = 60)
    private String lastName;
    @Column(unique = true, nullable = false, length = 60)
    @NotBlank(message = "username does not blank")
    private String username;
    @NotBlank(message = "password does not blank")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String password;
    private String profilePictureUrl;
    @Column(length = 15)
    @Pattern(regexp = "^((\\+|00)261|0)[23678][0-9]{7}$", message = "phone number malagasy should be in format +261 or 0XY...")
    private String phoneNumber;
    @NotBlank(message = "address does not blank")
    private String address;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<AppRole> appRoles;

}
