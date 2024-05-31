package com.example.practica2.security;

import com.example.practica2.exception.UnauthorizedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("hola entre al AccesDeniedHandler con peticion a: "+request.getRequestURI()+ " De tipo: "+request.getMethod());
        if (authentication != null) {
            System.out.println("User '" + authentication.getName() + "' attempted to access the URL: " + request.getRequestURI());
        }
        response.sendRedirect(request.getContextPath() + "/error/access-denied");
    }
}
