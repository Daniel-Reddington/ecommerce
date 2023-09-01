package com.backend.ecommerce.repositories;

import com.backend.ecommerce.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface CommandRepository extends JpaRepository<Command, Long> {
}
