package com.example.practica2.utils;

import com.example.practica2.expiration.Expiration;
import com.example.practica2.expiration.ExpirationRepo;
import com.example.practica2.security.Rol;
import com.example.practica2.security.RolRepository;
import com.example.practica2.user.User;
import com.example.practica2.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service

public class LoadDefaultData {
    private UserRepository userRepository;
    private ExpirationRepo expirationRepo;
    private PasswordEncoder passwordEncoder;
    private RolRepository rolRepository;

    public LoadDefaultData(UserRepository userRepository, ExpirationRepo expirationRepo, RolRepository rolRepository) {
        this.userRepository = userRepository;
        this.expirationRepo = expirationRepo;
        this.rolRepository = rolRepository;
    }

    public void createDefaultData(){
        createDefaultUser();
        createDefaultExpiration();
    }

    private void createDefaultExpiration() {
        if (expirationRepo.count() < 1){
            System.out.println("Creación de expiraciones en la base de datos");
            Expiration expiration = new Expiration("1 hora", 3600);
            expirationRepo.save(expiration);
            expiration = new Expiration("1 dia", 86400);
            expirationRepo.save(expiration);
            expiration = new Expiration("1 semana", 604800);
            expirationRepo.save(expiration);
            expiration = new Expiration("1 mes", 2628000);
            expirationRepo.save(expiration);
            expiration = new Expiration("1 agno", 31536000);
            expirationRepo.save(expiration);
        }
    }

    private void createDefaultUser(){
        if (!userRepository.existsUserByUsername("admin")){
            System.out.println("Creación del usuario y rol en la base de datos");
            Rol rolAdmin = new Rol(ERole.ROLE_ADMIN.name());
            Rol rolUsuario = new Rol(ERole.ROLE_USER.name());
            rolAdmin = rolRepository.save(rolAdmin);
            rolUsuario = rolRepository.save(rolUsuario);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setName("Administrador");
            admin.setActive(true);
            admin.setRoles(new HashSet<>(Arrays.asList(rolAdmin)));
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setName("Usuario");
            user.setActive(true);
            user.setRoles(new HashSet<>(Arrays.asList(rolUsuario)));
            userRepository.save(user);
        }
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        return passwordEncoder;
    }
}
