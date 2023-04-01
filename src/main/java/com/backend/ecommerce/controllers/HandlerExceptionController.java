package com.backend.ecommerce.controllers;

import com.backend.ecommerce.exceptions.AppRoleNotFoundException;
import com.backend.ecommerce.exceptions.AppUserNotFoundException;
import com.backend.ecommerce.exceptions.CategoryNotFoundException;
import com.backend.ecommerce.exceptions.exceptionForm.ExceptionForm;
import com.backend.ecommerce.exceptions.ProductNotFoundException;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class HandlerExceptionController {

    private final ApiResponseService apiResponseService;

    @ExceptionHandler(AppRoleNotFoundException.class)
    public ResponseEntity<ApiResponse> handleAppRoleNotFoundException(AppRoleNotFoundException exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleAppUserNotFoundException(AppUserNotFoundException exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCategoryNotFoundException(CategoryNotFoundException exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse> handleProductNotFoundException(ProductNotFoundException exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
        ExceptionForm exceptionForm = new ExceptionForm(errorMessage, LocalDateTime.now());
        return apiResponseService.createApiResponseForm(exceptionForm, false, HttpStatus.BAD_REQUEST);
    }


}
