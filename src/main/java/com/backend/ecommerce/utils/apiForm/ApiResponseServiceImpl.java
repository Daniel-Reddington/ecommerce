package com.backend.ecommerce.utils.apiForm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ApiResponseServiceImpl implements ApiResponseService {
    @Override
    public ResponseEntity<ApiResponse> createApiResponseForm(Object object, boolean status, HttpStatus httpStatus) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(object);
        apiResponse.setSuccess(status);
        apiResponse.setStatus(httpStatus);

        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
