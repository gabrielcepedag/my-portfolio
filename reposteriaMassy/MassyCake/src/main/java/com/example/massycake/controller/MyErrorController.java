package com.example.massycake.controller;

import com.example.massycake.entities.Empleado;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class MyErrorController implements ErrorController {

    @GetMapping("/access-denied")
    public String accesoDenegado(){
        return "error-403";
    }

    @GetMapping("")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        if (authentication == null) {
//            System.out.println("No estas logueado se va pal login.");
//            return "redirect:/login";
//        }

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
//            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
//                return "error-403";
//            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error-403";
            }
        }
        System.out.println("hay un error que no he manejado");
        return "error";
    }
}
