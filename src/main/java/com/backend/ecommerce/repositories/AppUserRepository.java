package com.backend.ecommerce.repositories;

import com.backend.ecommerce.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface AppUserRepository extends JpaRepository<AppUser, String> {

    AppUser findByUsername(String username);
    List<AppUser> findByUsernameContainsIgnoreCase(String username);

}
