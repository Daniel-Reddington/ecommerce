package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.Category;
import com.backend.ecommerce.entities.Product;
import com.backend.ecommerce.services.interfaces.ProductService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import com.backend.ecommerce.utils.deserializer.CategoryEditor;
import com.backend.ecommerce.validator.AddMethodValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "api/products")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {

    private final ProductService productService;
    private final ApiResponseService apiResponseService;
    private final ObjectMapper objectMapper;

    // util for swagger to bind category in productDto to Category object
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Category.class, new CategoryEditor(objectMapper));
    }

    @PostMapping(value = "add-product", consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<ApiResponse> addProduct(@RequestPart @Validated(AddMethodValidator.class) Product product,
                                                  @RequestPart MultipartFile productImage){
        return apiResponseService.createApiResponseForm(
                productService.createProduct(product, productImage) , true, HttpStatus.CREATED);
    }

    @PatchMapping("update-product")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<ApiResponse> updateProduct(@Validated @RequestBody Product product){
        return apiResponseService.createApiResponseForm(
                productService.updateProduct(product), true, HttpStatus.OK);

    }

    @PutMapping(value = "update-product-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<ApiResponse> updateProductImage(@RequestParam Long idProduct,@RequestParam String appUserId, @RequestPart MultipartFile productImage){
        return apiResponseService.createApiResponseForm(
                productService.updateProductImage(idProduct, appUserId,productImage),true,HttpStatus.OK);
    }

    @DeleteMapping("remove/{idProduct}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> removeProduct(@RequestParam Long idProduct, @RequestParam String appUserId){
        productService.removeProduct(idProduct, appUserId);
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
