package com.dariotintore.tesi.exerciseservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:4200", // Locale
                                "https://main.d3hghtz35iqeu6.amplifyapp.com/", // AWS Dario Tintore
                                "http://143.225.170.56:4200" // Server Università
                ).allowedHeaders(
                        "Access-Control-Allow-Headers",
                        "Access-Control-Allow-Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers",
                        "Origin", "Cache-Control",
                        "Content-Type",
                        "Authorization")
                .allowedMethods("DELETE", "GET", "POST", "PATCH", "PUT", "OPTIONS")
        		.allowCredentials(true);
    }
}