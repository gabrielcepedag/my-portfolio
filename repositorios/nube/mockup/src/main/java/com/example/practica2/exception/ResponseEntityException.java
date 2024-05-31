package com.example.practica2.exception;

import com.example.practica2.utils.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public class ResponseEntityException extends RuntimeException {
    private static final long serialVersionUID = -3156815846745801694L;

    private transient ResponseEntity<ApiResponse> apiResponse;

    public ResponseEntityException(ResponseEntity<ApiResponse> apiResponse) {
        this.apiResponse = apiResponse;
    }

    public ResponseEntity<ApiResponse> getApiResponse() {
        return apiResponse;
    }
}