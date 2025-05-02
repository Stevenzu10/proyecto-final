package com.tuapp.empresa_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // aplica a todas las rutas
                        .allowedOrigins("http://localhost:5173") // permite el frontend local
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // métodos permitidos
                        .allowedHeaders("*"); // permite todos los headers
            }
        };
    }
}
