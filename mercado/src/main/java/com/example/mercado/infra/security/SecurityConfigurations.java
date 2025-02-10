package com.example.mercado.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless JWT
                .authorizeHttpRequests(authorize -> authorize
                        // Acesso sem autenticação
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()

                        .requestMatchers(HttpMethod.GET, "/produtos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/produtos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/produtos/nome/contendo/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/marcas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/marcas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/marcas/nome/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/marcas/nome/contendo/**").permitAll()

                        // Acesso restrito por papel
                        .requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/marcas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/marcas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/marcas/**").hasRole("ADMIN")

                        // Permissões para o Swagger
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }




    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
