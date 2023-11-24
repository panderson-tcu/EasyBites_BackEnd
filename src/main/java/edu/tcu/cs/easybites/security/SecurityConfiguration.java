package edu.tcu.cs.easybites.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {

    @Value("${api.endpoint.base-url}")
    private String baseUrl;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // All recipe API endpoints should require authentication
                        // Here we only specify PUT - update recipe status for admins only
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, this.baseUrl + "/recipes/**/**")).hasAuthority("ROLE_admin")

                        // nutrition-user api endpoints
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, this.baseUrl + "/nutrition-user/**")).hasAuthority("ROLE_admin")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, this.baseUrl + "/nutrition-user")).hasAuthority("ROLE_admin") // login does not need admin
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, this.baseUrl + "/nutrition-user/**")).hasAuthority("ROLE_admin")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, this.baseUrl + "/nutrition-user/**")).hasAuthority("ROLE_admin")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll() // h2 console

                        .anyRequest().authenticated() // always good idea to put this as last to authenticate everything else.

                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
               .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
