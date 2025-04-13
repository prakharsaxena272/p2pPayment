package com.example.wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**") // Match all endpoints
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/h2-console/**",  // Allow access to H2 console
                                "/v3/api-docs/**", // Allow access for Swagger endpoints
                                "/swagger-ui/**",  // Allow access for Swagger UI
                                "/swagger-ui.html" // Allow access for Swagger UI HTML page
                        ).permitAll()
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .csrf(csrf -> csrf.disable()) // Explicitly disable CSRF protection
                .headers(headers -> headers.frameOptions().disable()); // Allow frames for H2 console

        return http.build();
    }
}