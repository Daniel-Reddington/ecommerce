package com.backend.ecommerce.controllers;

import com.backend.ecommerce.exceptions.*;
import com.backend.ecommerce.exceptions.exceptionForm.ExceptionForm;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class HandlerExceptionController {

    private final ApiResponseService apiResponseService;

    @ExceptionHandler(AppRoleNotFoundException.class)
    public ResponseEntity<ApiResponse> handleAppRoleNotFoundException(AppRoleNotFoundException exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        log.info("role exception", exception);
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleAppUserNotFoundException(AppUserNotFoundException exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        log.info("user exception");
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCategoryNotFoundException(CategoryNotFoundException exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        log.info("category exception");
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse> handleProductNotFoundException(ProductNotFoundException exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        log.info("product exception");
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFileNotFoundException(FileNotFoundException exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        log.info("file exception");
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        log.info("exception {}",exception);
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessages.add(violation.getMessage());
        }
        String errorMessage = String.join("; ", errorMessages);
        ExceptionForm exceptionForm = new ExceptionForm(errorMessage, LocalDateTime.now());
        log.info("constraint violation exception");
        return apiResponseService.createApiResponseForm(exceptionForm, false, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessages.add(error.getDefaultMessage());
        }
        log.info("exception {} ",ex.getMessage());
        log.info("exception {}",ex.getBody());
        log.info("exception {}", ex.getBindingResult());
        log.info("exception {}", ex.getDetailMessageArguments());
        log.info("exception {}", ex.getStatusCode());
        log.info("exception {}", ex.getParameter());
        String errorMessage = String.join("; ", errorMessages);
        ExceptionForm exceptionForm = new ExceptionForm(errorMessage, LocalDateTime.now());
        return apiResponseService.createApiResponseForm(exceptionForm, false, HttpStatus.BAD_REQUEST);
    }




}
