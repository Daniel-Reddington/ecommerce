package com.backend.ecommerce.dtos;

import com.backend.ecommerce.entities.AppRole;
import com.backend.ecommerce.validator.AddMethodValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountDto {

    @Email(message = "email must be a type email")
    @Size(max = 60, message = "email's character should be less than {max}")
    private String email;
    @Size(max = 60, message = "firstname max length should be {max}")
    private String firstName;
    @Size(max = 60, message = "lastname max length should be {max}")
    private String lastName;
    @NotBlank(message = "username does not blank", groups = AddMethodValidator.class)
    @Size(max = 60, message = "username max length should be {max}")
    private String username;
    @NotBlank(message = "password does not blank", groups = AddMethodValidator.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private MultipartFile profilePicture;
    @Size(max = 15, message = "phone number max length should be {max}")
    @Pattern(regexp = "^(\\+|0)[1-9]\\d{7,8}$", message = "Phone number should be in format +261XXXXXXXXX or 0XYXXXXXXXX")
    private String phoneNumber;
    @NotBlank(message = "address does not blank", groups = AddMethodValidator.class)
    @Size(max = 25, message = "address length should be less than {max}")
    private String address;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    Set<AppRole> appRoles = new LinkedHashSet<>();

}
