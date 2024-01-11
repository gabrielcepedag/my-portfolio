package com.example.massycake;

import com.example.massycake.entities.Empleado;
import com.example.massycake.service.EmpleadoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@SpringBootApplication
public class MassyCakeApplication {
	public static void main(String[] args) {
		SpringApplication.run(MassyCakeApplication.class, args);
		Empleado admin = new Empleado("000-0000000-0", "admin", "admin", "admin", "admin", "000-000-0000", "");


		}

}
