package com.backend.ecommerce.repositories;

import com.backend.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RepositoryRestResource
@ApiIgnore
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductNameContainsIgnoreCase(String productName);
    @Modifying
    @Query("UPDATE Product p SET p.stockQuantity = p.stockQuantity - :quantity WHERE p.id = :id")
    void decrementStockQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);


}
