package com.backend.ecommerce.repositories;

import com.backend.ecommerce.entities.CommandItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface CommandItemRepository extends JpaRepository<CommandItem, Long> {
}
