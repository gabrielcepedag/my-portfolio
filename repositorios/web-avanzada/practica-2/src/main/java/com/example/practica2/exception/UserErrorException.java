package com.example.practica2.exception;

import com.example.practica2.utils.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public class UserErrorException extends RuntimeException{

    private ResponseEntity<ApiResponse> apiResponse;

    public UserErrorException(ResponseEntity<ApiResponse> apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public UserErrorException(String message) {
        super(message);
    }
    public ResponseEntity<ApiResponse> getApiResponse() {
        return apiResponse;
    }

}