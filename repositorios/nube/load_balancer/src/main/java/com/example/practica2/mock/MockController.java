package com.example.practica2.mock;

import com.example.practica2.exception.ResponseEntityException;
import com.example.practica2.utils.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mock")
public class MockController {
    private MockService mockService;
    public MockController(MockService mockService) {
        this.mockService = mockService;
    }
    @ExceptionHandler(ResponseEntityException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityException exception) {
        System.out.println("Paso por ResponseEntityErrorException en MockController");
        return exception.getApiResponse();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> getMock(@PathVariable Long id){
        return mockService.getOneById(id);
    }

    @PostMapping(value = "/")
    public ResponseEntity<ApiResponse> createMock(@Valid @RequestBody MockRequest mockRequest){
        return mockService.save(mockRequest);
    }

}
