package com.example.practica2;

import com.example.practica2.security.SecurityServices;
import com.example.practica2.utils.LoadDefaultData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Practica2Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Practica2Application.class, args);

        LoadDefaultData loadDefaultData = (LoadDefaultData) applicationContext.getBean("loadDefaultData");
        loadDefaultData.createDefaultData();
    }

}
