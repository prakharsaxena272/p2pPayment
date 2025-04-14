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
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for APIs
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/h2-console/**",   // Allow access to H2 console
                                "/v3/api-docs/**",  // Allow Swagger API docs
                                "/swagger-ui/**",   // Allow Swagger UI
                                "/swagger-ui.html", // Allow Swagger UI HTML page
                                "/api/wallet/**"    // Allow access to wallet APIs
                        ).permitAll()            // Permit all above request matchers
                        .anyRequest().authenticated() // Secure any other endpoints
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .sameOrigin() // Allow frames from the same origin
                        )
                );

        return http.build();
    }
}