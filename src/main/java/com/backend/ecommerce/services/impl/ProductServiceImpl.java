package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.Product;
import com.backend.ecommerce.repositories.ProductRepository;
import com.backend.ecommerce.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public Product addProduct(Product product) {
        if(product == null) throw new RuntimeException("Product is null");

        product.setPublishDate(LocalDateTime.now());
        product = productRepository.save(product);

        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        return addProduct(product);
    }

    @Override
    public void removeProduct(Long idProduct) {
        if(idProduct == null) throw new RuntimeException("You can't remove product from id null");
        productRepository.deleteById(idProduct);
    }

    @Override
    public List<Product> findAllProduct() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public List<Product> findByProductNameContains(String productName) {
        List<Product> products = productRepository.findByProductNameContains(productName);
        return products;
    }

    @Override
    public Product findProductById(Long idProduct) {
        Product product = productRepository.findById(idProduct).orElse(null);
        return product;
    }
}
