package com.example.massycake.security;

import com.example.massycake.utils.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.massycake.security.UserDetailsServiceImpl;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;

@Configuration
public class WebSecurityConfig {
    @Value("${RememberMe.key}")
    private String rememberMeKey;
    @Autowired
    UserDetailsService userDetailsService;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    //    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//    }
    @Bean
    SecurityFilterChain web(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http
                .rememberMe((remember) -> remember
                        .rememberMeServices(rememberMeServices)
                )
                .csrf().disable() //Para poder aceptar peticiones POST
                .anonymous().disable()
                .authorizeHttpRequests((authorize) -> {
                            try {
                                authorize
                                        .requestMatchers("/error/**").permitAll()
                                        .requestMatchers("/assets/**", "/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()
                                        .requestMatchers("/pos/**").hasAnyRole(ERole.ADMIN.name(), ERole.CAJERO.name())
                                        .requestMatchers("/cocina/**").hasAnyRole(ERole.ADMIN.name(), ERole.REPOSTERO.name())
                                        .requestMatchers("/perfil").hasAnyRole(ERole.ADMIN.name(), ERole.CAJERO.name(), ERole.REPOSTERO.name())
                                        .requestMatchers("/inventario").hasAnyRole(ERole.REPOSTERO.name(), ERole.ADMIN.name())
                                        .requestMatchers("/").permitAll()
                                        .anyRequest().hasRole(ERole.ADMIN.name())
                                        .and()
                                        .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
//        http.logout((logout) -> logout.addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(COOKIES)))); //Elimina todas las cookies del navegador.
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.formLogin(form -> {
            try {
                form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
//                        .and().logout().logoutSuccessUrl("/logout")
                        .permitAll()
                        .and()
                        .logout()
                        .deleteCookies("JSESSIONID")
                        .deleteCookies("remember-me")
                        .and()
                        .rememberMe()
//                        .rememberMeParameter("")
                        .key(rememberMeKey)
                        .tokenValiditySeconds(86400000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        http.httpBasic(withDefaults());

        return http.build();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler();
    }
    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(rememberMeKey, userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        return rememberMe;
    }
    //    @Bean
//    public UserDetailsServiceImpl userDetailsService(){
//        return new UserDetailsServiceImpl();
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
//    @Bean
//    AuthenticationManager a(AuthenticationConfiguration authenticationConfiguration) throws Exception{
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}