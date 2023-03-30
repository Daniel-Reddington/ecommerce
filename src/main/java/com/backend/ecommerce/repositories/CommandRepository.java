package com.backend.ecommerce.repositories;

import com.backend.ecommerce.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long> {
}
