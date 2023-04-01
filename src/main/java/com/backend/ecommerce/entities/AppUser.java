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
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    private String id;
    @Column(length = 80)
    @Email(message = "email must be a type email")
    @Size(max = 60, message = "email's character should be less than {max}")
    private String email;
    @Column(length = 60)
    @Size(max = 60, message = "firstname max length should be {max}")
    private String firstName;
    @Column(length = 60)
    @Size(max = 60, message = "lastname max length should be {max}")
    private String lastName;
    @Column(unique = true, nullable = false, length = 60)
    @NotBlank(message = "username does not blank", groups = AddMethodValidator.class)
    @Size(max = 60, message = "username max length should be {max}")
    private String username;
    @NotBlank(message = "password does not blank", groups = AddMethodValidator.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String profilePictureUrl;
    @Column(length = 15)
    @Size(max = 15, message = "phone number max length should be {max}")
    @Pattern(regexp = "^(\\+|0)[1-9]\\d{7,8}$", message = "Phone number should be in format +261XXXXXXXXX or 0XYXXXXXXXX")
    private String phoneNumber;
    @NotBlank(message = "address does not blank", groups = AddMethodValidator.class)
    @Column(length = 25)
    @Size(max = 25, message = "address length should be less than {max}")
    private String address;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<AppRole> appRoles = new ArrayList<>();

}
