package com.example.massycake.controller;

import com.example.massycake.entities.Producto;
import com.example.massycake.payload.request.LoginRequest;
import com.example.massycake.payload.request.PruebaRequest;
import com.example.massycake.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PruebaController {
    private final ProductoService productoService;

    public PruebaController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@Valid @RequestBody PruebaRequest pruebaRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
        }

        System.out.println("Me llego ID: "+pruebaRequest.getId()+ " Con cantidad: "+pruebaRequest.getCantidad()+" y nota: "+pruebaRequest.getNota());
        System.out.println("\nMe llego la lista con size: "+pruebaRequest.getLista().size());

        for (LoginRequest str : pruebaRequest.getLista()) {
            System.out.println("Usuario: "+str.getUsuario()+ " Password: "+str.getPassword());
        }

        Producto p1 = productoService.getProductoById(pruebaRequest.getId());
        return ResponseEntity.status(HttpStatus.OK).body(p1);
    }

    @GetMapping("/prueba")
    public String prueba(){
        return "prueba";
    }
}