package com.example.massycake.security;

import com.example.massycake.entities.Empleado;
import com.example.massycake.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    EmpleadoService empleadoService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado user = empleadoService.getOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return User
                .withUsername(username)
                .password(user.getPassword())
                .roles(user.getRol())
                .build();
    }
}
