package com.backend.ecommerce.repositories;

import com.backend.ecommerce.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    List<AppUser> findByUsernameContains(String username);
}
