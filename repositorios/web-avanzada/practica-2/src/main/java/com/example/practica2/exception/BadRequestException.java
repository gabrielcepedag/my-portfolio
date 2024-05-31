package com.example.practica2.exception;

import com.example.practica2.utils.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ResponseEntity<ApiResponse> apiResponse;

    public BadRequestException(ResponseEntity<ApiResponse> apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public BadRequestException(String message) {
        super(message);

    }
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseEntity<ApiResponse> getApiResponse() {
        return apiResponse;
    }
}