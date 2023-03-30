package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.entities.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void removeProduct(Long idProduct);
    List<Product> findAllProduct();
    List<Product> findByProductNameContains(String productName);
    Product findProductById(Long idProduct);
    List<Product> findAllProductByIds(List<Long> productIds);
    void decrementStockQuantity(Long idProduct, Integer decrementStockQuantity);

}
