package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.Category;
import com.backend.ecommerce.services.interfaces.CategoryService;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/categories")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ApiResponseService apiResponseService;

    @PostMapping("add-category")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> addCategory(@Validated @RequestBody Category category){
        return apiResponseService.createApiResponseForm(
                categoryService.addCategory(category),true,HttpStatus.CREATED);
    }

    @PatchMapping("update-category")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> updateCategory(@Validated @RequestBody Category category){
        return apiResponseService.createApiResponseForm(categoryService.updateCategory(category),true,HttpStatus.OK);
    }

    @DeleteMapping("remove-category/{idCategory}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse> removeCategory(@PathVariable Integer idCategory){
        categoryService.removeCategory(idCategory);
        return apiResponseService.createApiResponseForm(null,true,HttpStatus.OK);
    }

    @GetMapping("find-by-id/{idCategory}")
    public ResponseEntity<ApiResponse> findCategoryById(@PathVariable Integer idCategory){
        return apiResponseService.createApiResponseForm(
                categoryService.findCategoryById(idCategory),true,HttpStatus.OK);
    }

    @GetMapping("find-all-category")
    public ResponseEntity<ApiResponse> findAllCategory(){
        return apiResponseService.createApiResponseForm(categoryService.findAllCategory(),true,HttpStatus.OK);
    }

    @GetMapping("find-all-product-in-category/{idCategory}")
    public ResponseEntity<ApiResponse> findAllProductInCategory(@PathVariable Integer idCategory){
        return apiResponseService.createApiResponseForm(
                categoryService.findAllProductInCategory(idCategory),true,HttpStatus.OK);
    }
}
