package com.example.p7_functions.utils;

public class ApiResponse<T> {
    boolean error;
    int code;
    String message;
    T data;

    public ApiResponse() {
    }

    public ApiResponse(boolean error, int code, String message, T data) {
        this.error = error;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
