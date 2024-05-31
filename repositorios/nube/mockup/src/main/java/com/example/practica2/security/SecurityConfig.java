package com.example.practica2.security;

import com.example.practica2.utils.ERole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {
    private SecurityServices securityServices;
    private PasswordEncoder passwordEncoder;
    private AccessDeniedHandler accessDeniedHandler;
    public SecurityConfig(SecurityServices securityServices, PasswordEncoder passwordEncoder, AccessDeniedHandler accessDeniedHandler) {
        this.securityServices = securityServices;
        this.passwordEncoder = passwordEncoder;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests(authorization ->
                    authorization
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/error/**"), AntPathRequestMatcher.antMatcher("/login")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/css/**"), AntPathRequestMatcher.antMatcher("/images/**"), AntPathRequestMatcher.antMatcher("/js/**"), AntPathRequestMatcher.antMatcher("*.html")).permitAll()
                        .requestMatchers(mvc.pattern("/"), mvc.pattern("/home") ,mvc.pattern("/logout")).permitAll()
                        .requestMatchers(mvc.pattern("/menu")).authenticated()
                        .requestMatchers(mvc.pattern("/user/**")).hasRole(ERole.ADMIN.name())
                        .requestMatchers(mvc.pattern("/gestion/**")).hasRole(ERole.ADMIN.name())
                        .requestMatchers(mvc.pattern("/project/**")).authenticated()
                        .requestMatchers(mvc.pattern("/mock/**")).authenticated()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api-docs/**"), AntPathRequestMatcher.antMatcher("/api-docs.yaml"), AntPathRequestMatcher.antMatcher("/swagger-ui.html"), AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll() //para OpenApi
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).authenticated()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).authenticated()
                    .anyRequest().authenticated()
//                        authorization
//                                .requestMatchers(mvc.pattern("/")).permitAll() //permitiendo llamadas a esas urls.
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/css/**"), AntPathRequestMatcher.antMatcher("/js/**"), AntPathRequestMatcher.antMatcher("/webjars/**"), AntPathRequestMatcher.antMatcher("*.html")).permitAll() //permitiendo llamadas a esas urls.
//                                .requestMatchers(mvc.pattern("/h2-console/**")).permitAll()
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/thymeleaf/**"), AntPathRequestMatcher.antMatcher("/freemarker/**"), AntPathRequestMatcher.antMatcher("/api/**"), AntPathRequestMatcher.antMatcher("/jpa/**")).permitAll()
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/api-docs/**"), AntPathRequestMatcher.antMatcher("/api-docs.yaml"), AntPathRequestMatcher.antMatcher("/swagger-ui.html"), AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll() //para OpenApi
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/")).hasAnyRole("ADMIN", "USER")
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/estudiantes/")).permitAll() //hasAnyRole("ADMIN", "USER")
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/logout")).permitAll()
//                                .anyRequest().authenticated() //cualquier llamada debe ser validada
                    )
                .anonymous((anonymous) -> anonymous.disable())
                .csrf((csrf) -> csrf.disable())
                .exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler)
//                        .accessDeniedPage("/error/access-denied")
                )
                .formLogin((form) -> form
                        .loginPage("/auth") //indicando la ruta que estaremos utilizando.
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                        .failureUrl("/auth?error")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
//                        .logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/logout"))
                        .permitAll());
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);

        auth.userDetailsService(securityServices).passwordEncoder(passwordEncoder);
        return auth.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
}
