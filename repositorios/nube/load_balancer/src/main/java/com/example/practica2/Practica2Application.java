package com.example.practica2;

import com.example.practica2.utils.LoadDefaultData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
//@EnableRedisHttpSession
public class Practica2Application {
    private final static String SESSIONS_MAP_NAME = "EJEMPLO-REDIS";

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Practica2Application.class, args);

        LoadDefaultData loadDefaultData = (LoadDefaultData) applicationContext.getBean("loadDefaultData");
        loadDefaultData.createDefaultData();
    }


}
