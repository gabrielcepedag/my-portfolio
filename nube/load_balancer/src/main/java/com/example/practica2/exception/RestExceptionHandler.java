package com.example.practica2.exception;

import com.example.practica2.utils.response.ApiResponse;
import com.example.practica2.utils.response.CustResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler {
    private CustResponseBuilder custResponseBuilder;

    public RestExceptionHandler(CustResponseBuilder custResponseBuilder) {
        this.custResponseBuilder = custResponseBuilder;
    }

    public ResponseEntity<ApiResponse> resolveException(UserErrorException exception) {
        System.out.println("Paso por UserErrorException: ");
        return exception.getApiResponse();
    }
    @ExceptionHandler(UnauthorizedException.class)
//    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String resolveException(UnauthorizedException exception, Model model) {
        System.out.println("Paso por UnauthorizedException: ");

        model.addAttribute("errorCode", exception.getApiResponse().getStatusCode().value());
        model.addAttribute("errorMessage", exception.getApiResponse().getBody().getMessage());

        return "error";
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> resolveException(BadRequestException exception) {
        System.out.println("Paso por BadRequestException: ");
        return exception.getApiResponse();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseBody
    public String resolveException(ResourceNotFoundException exception, Model model) {
        System.out.println("ResourceNotFoundException: resolveException");

        model.addAttribute("errorCode", exception.getApiResponse().getStatusCode().value());
        model.addAttribute("errorMessage", exception.getApiResponse().getBody().getMessage());

        return "404";
//        return exception.getApiResponse();
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> resolveException(MethodArgumentNotValidException ex) {
        System.out.println("MethodArgumentNotValidException: resolveException");
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder messages = new StringBuilder();

        for (FieldError error : fieldErrors) {
            messages.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(". ");
        }
        return custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), messages.toString(), HttpStatus.BAD_REQUEST.getReasonPhrase() );
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> resolveException(MethodArgumentTypeMismatchException ex) {
        System.out.println("MethodArgumentTypeMismatchException: resolveException");

        String message = "Parameter '" + ex.getParameter().getParameterName() + "' must be '"
                + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + "'";
        return custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), message, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> resolveException(HttpMessageNotReadableException ex) {
        System.out.println("HttpMessageNotReadableException: resolveException");

        String message = "Please provide Request Body in valid JSON format";
        return custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }
    @ExceptionHandler({ MissingServletRequestParameterException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> resolveException(MissingServletRequestParameterException ex) {
        System.out.println("MissingServletRequestParameterException: resolveException");
//        String message = "Please provide all The Parameters in a Valid Format";
        return custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }



}
