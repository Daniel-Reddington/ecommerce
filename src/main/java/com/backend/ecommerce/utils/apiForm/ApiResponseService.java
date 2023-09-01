package com.backend.ecommerce.utils.apiForm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ApiResponseService {

    ResponseEntity<ApiResponse> createApiResponseForm(Object object, boolean status, HttpStatus httpStatus);

}
