package com.backend.ecommerce.repositories;

import com.backend.ecommerce.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AppRoleRepository extends JpaRepository<AppRole, Integer> {

    AppRole findByRoleName(String roleName);

}
