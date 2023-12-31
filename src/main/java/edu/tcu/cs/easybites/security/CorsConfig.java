package edu.tcu.cs.easybites.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**"); // Enable CORS for the whole application.
            }
        };
    }

//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        // Allow all origins, you can customize this to allow only specific origins
//        config.addAllowedOrigin("*");
//
//        // Allow all HTTP methods (GET, POST, PUT, etc.)
//        config.addAllowedMethod("*");
//
//        // Allow all headers
//        config.addAllowedHeader("*");
//
//        // Expose headers if needed
//        config.addExposedHeader("header1");
//        config.addExposedHeader("header2");
//
//        source.registerCorsConfiguration("/**", config);
//
//        return new CorsFilter(source);
//    }
}