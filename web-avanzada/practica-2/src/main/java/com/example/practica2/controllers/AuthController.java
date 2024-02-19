package com.example.practica2.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    public AuthController() {
    }

    @GetMapping("/auth")
    public String loginRegisterPage(Model model,@RequestParam(required = false) String method) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            return "redirect:/";
        }
        model.addAttribute("valid", true);

//        model.addAttribute("login", (method != null && method.equalsIgnoreCase("login")));
        model.addAttribute("login", true);
        return "authentication";
    }
}
