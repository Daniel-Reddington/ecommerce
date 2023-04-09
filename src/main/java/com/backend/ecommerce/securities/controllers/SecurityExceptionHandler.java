package com.backend.ecommerce.securities.controllers;

import com.backend.ecommerce.exceptions.exceptionForm.ExceptionForm;
import com.backend.ecommerce.securities.exceptions.BadCredentialSecurityException;
import com.backend.ecommerce.utils.apiForm.ApiResponse;
import com.backend.ecommerce.utils.apiForm.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApiResponseService apiResponseService;

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(Exception exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        return apiResponseService.createApiResponseForm(exceptionForm, false, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialSecurityException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialException(BadCredentialSecurityException exception){
        ExceptionForm exceptionForm = new ExceptionForm(exception.getMessage(), LocalDateTime.now());
        return apiResponseService.createApiResponseForm(exceptionForm,false, HttpStatus.UNAUTHORIZED);
    }

}
