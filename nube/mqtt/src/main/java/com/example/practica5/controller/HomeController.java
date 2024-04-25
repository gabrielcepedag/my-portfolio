package com.example.practica5.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${SENSOR_COUNT}")
    private int sensorCount;

    public HomeController() {}

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("sensorCount", sensorCount);

        return "home";
    }
}
