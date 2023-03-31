package com.backend.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRole {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String roleName;
    @ManyToMany(mappedBy = "appRoles",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AppUser> appUsers;

    @Override
    public String toString() {
        return "AppRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }

}
