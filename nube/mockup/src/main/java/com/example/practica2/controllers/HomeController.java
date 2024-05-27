package com.example.practica2.controllers;

import com.example.practica2.expiration.Expiration;
import com.example.practica2.mock.Mock;
import com.example.practica2.project.Project;
import com.example.practica2.project.ProjectService;
import com.example.practica2.security.UserLoggued;
import com.example.practica2.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private ProjectService projectService;
    private UserLoggued userLoggued;

    public HomeController(ProjectService projectService, UserLoggued userLoggued) {
        this.projectService = projectService;
        this.userLoggued = userLoggued;

    }

    @GetMapping({"/", "/home"})
    public String homePage(Model model) {
        model.addAttribute("test", "Testing Template");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            return "redirect:/menu";
        }

        return "home";
    }

    @GetMapping("/menu")
    public String mainMenuPage(Model model) {
        User userLogged = userLoggued.getUserLoggued();
        model.addAttribute("userLogged",  userLogged);

        model.addAttribute("projectList",  projectService.getAll().getBody().getData());
        return "mainMenu";
    }
}
