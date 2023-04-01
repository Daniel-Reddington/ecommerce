package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.CommandItem;
import com.backend.ecommerce.entities.Product;
import com.backend.ecommerce.exceptions.ProductNotFoundException;
import com.backend.ecommerce.repositories.ProductRepository;
import com.backend.ecommerce.services.interfaces.CategoryService;
import com.backend.ecommerce.services.interfaces.CommandItemService;
import com.backend.ecommerce.services.interfaces.ProductService;
import com.backend.ecommerce.utils.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    private final ProductMapper productMapper;

    @Override
    public Product addProduct(Product product) {
        if(product == null) throw new ProductNotFoundException("Product is null");
        categoryService.findCategoryById(product.getCategory().getId());
        product.setPublishDate(LocalDateTime.now());
        product = productRepository.save(product);

        return product;
    }

    @Override
    public Product updateProduct(Product currentProduct) {
        Product product = findProductById(currentProduct.getId());

        currentProduct.setPublishDate(LocalDateTime.now());
        Product updatedProduct = productMapper.updateProduct(product, currentProduct);

        return productRepository.save(updatedProduct);
    }

    @Override
    public void removeProduct(Long idProduct) {
        List<CommandItem> commandItems = findProductById(idProduct).getCommandItems();
        productRepository.deleteById(idProduct);
    }

    @Override
    public List<Product> findAllProduct() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()) throw new ProductNotFoundException("Nothing products found");
        return products;
    }

    @Override
    public List<Product> findByProductNameContains(String productName) {
        List<Product> products = productRepository.findByProductNameContainsIgnoreCase(productName);
        if(products.isEmpty()) throw new ProductNotFoundException("Nothing products with name contains "+productName);
        return products;
    }

    @Override
    public Product findProductById(Long idProduct) {
        Product product = productRepository.findById(idProduct).orElseThrow(()->
                new ProductNotFoundException("Product for id '"+idProduct+"' not found"));
        return product;
    }

    @Override
    public List<Product> findAllProductByIds(List<Long> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        if(products.isEmpty()) throw new ProductNotFoundException("Nothing products found");
        return products;
    }
    @Override
    public boolean isProductQuantityAvailable(CommandItem item) {
        return item.getQuantity() <= item.getProduct().getStockQuantity();
    }


    @Override
    public void decrementStockQuantity(Long idProduct, Integer decrementQuantity) {
        productRepository.decrementStockQuantity(idProduct, decrementQuantity);
    }

}
