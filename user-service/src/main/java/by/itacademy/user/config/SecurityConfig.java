package by.itacademy.user.config;

import by.itacademy.user.controller.filter.JwtFilter;
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
                        .requestMatchers(HttpMethod.POST,"/users/registration" ).permitAll()
                        .requestMatchers(HttpMethod.POST,"/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/users/verification").permitAll()
                        .requestMatchers(HttpMethod.GET,"/users/me").authenticated()
                        .requestMatchers(HttpMethod.GET,"/users").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/users/{uuid}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/users").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/users").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // Add JWT token filter

                .addFilterBefore(filter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}