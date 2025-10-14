package com.yrmultimediaco.uber_clone_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Cross-Site Request Forgery (CSRF) protection ko disable karna (APIs ke liye zaroori)
                .csrf(csrf -> csrf.disable())

                // Authorization rules define karna (Modern Spring Security syntax)
                .authorizeHttpRequests(authorize -> authorize
                        // 🔥 1. Health check ko allow karna (GET request)
                        .requestMatchers(HttpMethod.GET, "/api/health").permitAll()

                        // 🔥 2. Ride route request ko allow karna (POST request)
                        .requestMatchers(HttpMethod.POST, "/api/rides/route").permitAll()

                        // Baaki saare endpoints ke liye authentication (sign-in) zaroori hai
                        .anyRequest().authenticated()
                )
                // Default login forms aur basic popups ko disable karte hain
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable());

        return http.build();
    }
}
