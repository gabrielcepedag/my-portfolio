package com.example.practica2.utils;

public enum ApiResponseCode {
    SUCCESS(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    ApiResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

//    public static ApiResponseCode getByCode(int code) {
//        for (ApiResponseCode responseCode : ApiResponseCode.values()) {
//            if (responseCode.getCode() == code) {
//                return responseCode;
//            }
//        }
//        throw new IllegalArgumentException("Código de respuesta no válido: " + code);
//    }
}
