package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.entities.CommandItem;
import com.backend.ecommerce.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product, MultipartFile productImage);
    Product addProduct(Product product);
    Product updateProduct(Product product);
    Product updateProductImage(Long idProduct, String appUserId, MultipartFile productImage);
    void removeProduct(Long idProduct, String appUserId);
    List<Product> findAllProduct();
    List<Product> findByProductNameContains(String productName);
    Product findProductById(Long idProduct);
    List<Product> findAllProductByIds(List<Long> productIds);
    boolean isProductQuantityAvailable(CommandItem commandItem);
    void decrementStockQuantity(Long idProduct, Integer decrementStockQuantity);

}
