package com.backend.ecommerce.repositories;

import com.backend.ecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import springfox.documentation.annotations.ApiIgnore;

@RepositoryRestResource
@ApiIgnore
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
