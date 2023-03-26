package com.backend.ecommerce.repositories;

import com.backend.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RepositoryRestResource
@ApiIgnore
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductNameContainsIgnoreCase(String productName);

}
