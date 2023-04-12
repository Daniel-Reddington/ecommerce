package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.dtos.ProductDto;
import com.backend.ecommerce.entities.Category;
import com.backend.ecommerce.entities.CommandItem;
import com.backend.ecommerce.entities.Product;
import com.backend.ecommerce.exceptions.ProductNotFoundException;
import com.backend.ecommerce.repositories.ProductRepository;
import com.backend.ecommerce.services.interfaces.CategoryService;
import com.backend.ecommerce.services.interfaces.ProductService;
import com.backend.ecommerce.services.interfaces.FileService;
import com.backend.ecommerce.utils.constants.FileDirectory;
import com.backend.ecommerce.utils.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final FileService fileService;
    private final ProductMapper productMapper;
    private static final String directory = FileDirectory.productImageDirectory;

    @Override
    public Product createProduct(Product product, MultipartFile productImage) {
        if(product == null) throw new ProductNotFoundException("Product is required");

        String productImageUrl = fileService.saveFile(productImage, directory);

        product.setProductImageUrl(productImageUrl);
        Product savedProduct = addProduct(product);
        return savedProduct;
    }

    @Override
    public Product addProduct(Product product) {
        Category categoryById = categoryService.findCategoryById(product.getCategory().getId());
        product.setCategory(categoryById);
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
    public Product updateProductImage(Long idProduct, MultipartFile productImage) {
        Product product = findProductById(idProduct);
        String productImageUrl = fileService.saveFile(productImage, directory);
        fileService.deleteFile(product.getProductImageUrl());
        product.setProductImageUrl(productImageUrl);
        return product;
    }

    @Override
    public void removeProduct(Long idProduct) {
        Product product = findProductById(idProduct);
        productRepository.deleteById(idProduct);
        fileService.deleteFile(product.getProductImageUrl());
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
