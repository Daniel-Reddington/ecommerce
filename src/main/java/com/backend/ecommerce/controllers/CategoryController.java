package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entities.Category;
import com.backend.ecommerce.services.interfaces.CategoryService;
import com.backend.ecommerce.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("add-category")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(categoryService.addCategory(category));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.CREATED);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @PatchMapping("update-category")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(categoryService.updateCategory(category));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @DeleteMapping("remove-category/{idCategory}")
    public ResponseEntity<ApiResponse> removeCategory(@PathVariable Integer idCategory){
        ApiResponse apiResponse = new ApiResponse();
        categoryService.removeCategory(idCategory);
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @GetMapping("find-by-id/{idCategory}")
    public ResponseEntity<ApiResponse> findCategoryById(@PathVariable Integer idCategory){
        ApiResponse apiResponse = new ApiResponse();
        Category category = categoryService.findCategoryById(idCategory);
        apiResponse.setData(category);
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.FOUND);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @GetMapping("find-all-category")
    public ResponseEntity<ApiResponse> findAllCategory(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(categoryService.findAllCategory());
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.FOUND);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @GetMapping("find-all-product-in-category/{idCategory}")
    public ResponseEntity<ApiResponse> findAllProductInCategory(@PathVariable Integer idCategory){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(categoryService.findAllProductInCategory(idCategory));
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.FOUND);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
