package com.example.practica2.security;

import com.example.practica2.exception.ResourceNotFoundException;
import com.example.practica2.user.User;
import com.example.practica2.user.UserRepository;
import com.example.practica2.utils.ERole;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SecurityServices implements UserDetailsService {
    private UserRepository userRepository;
    private RolRepository rolRepository;
    private PasswordEncoder passwordEncoder;

    public SecurityServices(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        System.out.println("Autenticación JPA");
        User user = null;
        Optional<User> u1 = userRepository.findByUsername(username);
        if(u1.isEmpty()){
            throw new ResourceNotFoundException(User.class.getSimpleName(), "USERNAME", username);
        }else{
            user = u1.get();
        }

        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Rol role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                true,
                true,
                true,
                grantedAuthorities);
    }
}
