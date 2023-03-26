package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.Product;
import com.backend.ecommerce.services.interfaces.ProductService;
import com.backend.ecommerce.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("add-product")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody Product product){

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(productService.addProduct(product));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.CREATED);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }

    @PatchMapping("update-product")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody Product product){

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(productService.updateProduct(product));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.CREATED);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }

    @DeleteMapping("remove/{idProduct}")
    public ResponseEntity<ApiResponse> removeProduct(@PathVariable Long idProduct){

        productService.removeProduct(idProduct);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }

    @GetMapping("find-all-product")
    public ResponseEntity<ApiResponse> findAllProducts(){

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(productService.findAllProduct());
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.FOUND);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }

    @GetMapping("find-by-product-name/{productName}")
    public ResponseEntity<ApiResponse> findByProductNameContains(@PathVariable String productName){

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(productService.findByProductNameContains(productName));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.FOUND);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }

    @GetMapping("find-by-id/{idProduct}")
    public ResponseEntity<ApiResponse> findProductById(Long idProduct){

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(productService.findProductById(idProduct));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.FOUND);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }


}
