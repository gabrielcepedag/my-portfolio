package com.example.practica2.exception;

import com.example.practica2.utils.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    private ResponseEntity<ApiResponse> apiResponse;

    public UnauthorizedException(ResponseEntity<ApiResponse> apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }


    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseEntity<ApiResponse> getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(ResponseEntity<ApiResponse> apiResponse) {
        this.apiResponse = apiResponse;
    }

}