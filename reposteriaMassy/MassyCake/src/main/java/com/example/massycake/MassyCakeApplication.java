package com.example.massycake;

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

		//TODO: CREAR ENTIDAD PEDIDO Y RECETAS
		//TODO: CUANDO SE CREE lA ENTIDAD PEDIDO SE DEBE CAMBIAR EL FACTORY PARA QUE EL DESCUENTO DEL PERSONALIZADO PASE AL PRODUCTO
		SpringApplication.run(MassyCakeApplication.class, args);
	}

}
