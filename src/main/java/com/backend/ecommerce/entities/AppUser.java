package com.backend.ecommerce.entities;

import com.backend.ecommerce.validator.AddMethodValidator;
import com.backend.ecommerce.validator.UpdateMethodValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
    @Null(message = "username should be null when update account", groups = UpdateMethodValidator.class)
    private String username;

    @NotBlank(message = "password does not blank", groups = AddMethodValidator.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Null(message = "password should be null when update account", groups = UpdateMethodValidator.class)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String profilePictureUrl;

    @Column(length = 15)
    @Size(max = 15, message = "phone number max length should be {max}")
    @Pattern(regexp = "^([+0])[1-9]\\d{7,8}$", message = "Phone number should be in format +261XXXXXXXXX or 0XYXXXXXXXX")
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
    Set<AppRole> appRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "appUser")
    @JsonIgnore
    @ToString.Exclude
    List<Command> commands;

    @OneToMany(mappedBy = "appUser")
    @JsonIgnore
    @ToString.Exclude
    List<Product> products;

}
