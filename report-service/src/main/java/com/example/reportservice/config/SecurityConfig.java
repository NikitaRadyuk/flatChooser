package com.example.reportservice.config;

import com.example.reportservice.controller.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception  {
        // Enable CORS and disable CSRF
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)

                // Set session management to stateless

                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Set unauthorized requests exception handler

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(
                                        (request, response, ex) -> response.setStatus(
                                                HttpServletResponse.SC_UNAUTHORIZED
                                        )
                                )
                                .accessDeniedHandler(
                                        (request, response, ex) -> response.setStatus(
                                                HttpServletResponse.SC_FORBIDDEN
                                        )
                                )
                )

                // Set permissions on endpoints

                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, "/report/{type}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/report/{UUID}/export").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.HEAD, "/report/{id}/export").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/report").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/audit").hasAnyRole("SYSTEM")
                )

                // Add JWT token filter

                .addFilterBefore(filter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
