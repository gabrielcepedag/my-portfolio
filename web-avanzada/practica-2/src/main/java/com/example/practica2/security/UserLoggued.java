package com.example.practica2.security;

import com.example.practica2.exception.BadRequestException;
import com.example.practica2.exception.UnauthorizedException;
import com.example.practica2.user.User;
import com.example.practica2.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserLoggued {
    private UserService userService;

    public UserLoggued(UserService userService) {
        this.userService = userService;
    }
    public User getUserLoggued(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails springSecurityUser = null;
        String userName = null;

        if(authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
                System.out.println("Usuario logueado 1: "+userName);
                return this.userService.getOneByUsername(userName).orElse(null);
//            } else if (authentication.getPrincipal() instanceof String) {
//                userName = (String) authentication.getPrincipal();
//                System.out.println("Usuario logueado 2: "+userName);
//                return this.userService.getOneByUsername("admin").orElse(null); //esto es de prueba

//                throw new BadRequestException("USUARIO NO ESTA LOGUEADO...");
            }
        }
        System.out.println("Usuario No esta logueado");

        throw new UnauthorizedException("USUARIO NO ESTA LOGUEADO...", new Throwable("USER NOT LOGGED"));
    }
    public boolean userLogguedHasRole(String rol){
        User user = getUserLoggued();
        for (Rol r1 : user.getRoles()) {
            if (r1.equals(rol)){
                return true;
            }
        }
        return false;
    }
}
