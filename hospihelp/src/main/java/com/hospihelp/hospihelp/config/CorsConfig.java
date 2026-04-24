package com.hospihelp.hospihelp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Permite request-uri de la frontendl colegului
        config.setAllowedOrigins(List.of(
                "http://localhost:8080",
                "http://127.0.0.1:8080"
        ));

        // Permite toate metodele HTTP
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        // Permite headerele necesare inclusiv Authorization pentru JWT
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept"
        ));

        // Permite trimiterea credentialelor (tokenul JWT)
        config.setAllowCredentials(true);

        // Aplica configuratia pe toate endpoint-urile
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}