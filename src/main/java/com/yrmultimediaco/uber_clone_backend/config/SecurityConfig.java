package com.yrmultimediaco.uber_clone_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // WebSocket aur APIs ke liye CSRF disable karna compulsory hai
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 1. WebSocket Handshake ko allow karein
                        .requestMatchers("/ws-uber/**").permitAll()

                        // 2. Directions API ko allow karein
                        .requestMatchers(HttpMethod.POST, "/api/rides/route").permitAll()

                        // 3. Driver Location aur Ride Requests ko allow karein
                        .requestMatchers("/api/rides/update-location").permitAll()
                        .requestMatchers("/api/rides/request").permitAll()
                        .requestMatchers("/api/rides/accept").permitAll()

                        // 4. Health check
                        .requestMatchers(HttpMethod.GET, "/api/health").permitAll()

                        // Abhi ke liye baaki sab bhi allow karte hain taaki testing mein rukawat na aaye
                        .anyRequest().permitAll()
                )
                .httpBasic(hb -> hb.disable())
                .formLogin(fl -> fl.disable());

        return http.build();
    }
}