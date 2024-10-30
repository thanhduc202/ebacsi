package com.thanh.ebacsi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // Allow all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow all methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(false)  // Set to true if credentials (cookies, authorization headers) are required
                .maxAge(3600);  // Cache the CORS preflight response for 1 hour
    }
}
