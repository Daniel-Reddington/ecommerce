package com.backend.ecommerce.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private Object data;
    private boolean isSuccess;
    private HttpStatus status;

}
