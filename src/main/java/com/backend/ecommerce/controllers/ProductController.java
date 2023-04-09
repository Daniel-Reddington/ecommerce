package com.backend.ecommerce.controllers;

import com.backend.ecommerce.dtos.ProductDto;
import com.backend.ecommerce.entities.Product;
import com.backend.ecommerce.services.interfaces.ProductService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import com.backend.ecommerce.validator.AddMethodValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ApiResponseService apiResponseService;

    @PostMapping("add-product")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<ApiResponse> addProduct(@Validated(AddMethodValidator.class) @RequestBody ProductDto productDto){
        return apiResponseService.createApiResponseForm(
                productService.createProduct(productDto), true, HttpStatus.CREATED);

    }

    @PatchMapping("update-product")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<ApiResponse> updateProduct(@Validated @RequestBody Product product){
        return apiResponseService.createApiResponseForm(
                productService.updateProduct(product), true, HttpStatus.OK);

    }

    @DeleteMapping("remove/{idProduct}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<ApiResponse> removeProduct(@PathVariable Long idProduct){
        productService.removeProduct(idProduct);
        return apiResponseService.createApiResponseForm(null, true, HttpStatus.OK);
    }

    @GetMapping("find-all-product")
    public ResponseEntity<ApiResponse> findAllProducts(){
        return apiResponseService.createApiResponseForm(productService.findAllProduct(), true, HttpStatus.OK);
    }

    @GetMapping("find-by-product-name-contains/{productName}")
    public ResponseEntity<ApiResponse> findByProductNameContains(@PathVariable String productName){
        return apiResponseService.createApiResponseForm(
                productService.findByProductNameContains(productName), true, HttpStatus.OK);

    }

    @GetMapping("find-by-id/{idProduct}")
    public ResponseEntity<ApiResponse> findProductById(Long idProduct){
        return apiResponseService.createApiResponseForm(
                productService.findProductById(idProduct), true, HttpStatus.OK);
    }


}
