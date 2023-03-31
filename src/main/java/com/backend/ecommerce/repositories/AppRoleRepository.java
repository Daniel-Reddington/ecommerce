package com.backend.ecommerce.repositories;

import com.backend.ecommerce.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Integer> {
}
