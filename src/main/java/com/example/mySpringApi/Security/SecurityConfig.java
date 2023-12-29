package com.example.mySpringApi.Security;

import com.example.mySpringApi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig configures the security settings for the Spring Boot application.
 * This configuration class sets up the security policy for HTTP requests, specifying which
 * endpoints are secured, the level of access required for each, and the authentication mechanisms.
 * <p>
 * Annotations:
 * - @Configuration: Marks this class as a configuration class for Spring, allowing it to be used
 *   by the Spring IoC container for dependency injection.
 * - @EnableWebSecurity: Enables Spring Security's web security support, activating security settings.
 * - @EnableMethodSecurity(prePostEnabled = true): Enables method-level security, allowing for
 *   annotations like @PreAuthorize to be used for securing individual method calls based on roles.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for SecurityConfig.
     *
     * @param customUserDetailsService A custom service to load user-specific data.
     * @param passwordEncoder The password encoder to be used for encoding user passwords.
     */
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Defines the security filter chain that dictates the security policy for HTTP requests.
     * <p>
     * This method configures which endpoints require authentication, the level of access needed, and
     * the type of authentication mechanism used. It sets up URL-based security restrictions, and
     * specifies CSRF protection and HTTP Basic authentication settings.
     *
     * @param http The HttpSecurity object to be configured.
     * @return A SecurityFilterChain object representing the configured security policies.
     * @throws Exception if there is a problem during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("DEBUG: I am in the securityFilterChain Security method");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/v3/api-docs").hasRole("ADMIN")  // Example endpoint restricted to ADMIN
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    /**
     * Configures the global authentication settings by specifying the userDetailsService and passwordEncoder.
     * <p>
     * This method sets up the authentication manager with a custom user details service for loading user data
     * and a password encoder for password hashing. This configuration is used for validating user credentials
     * during authentication.
     *
     * @param auth The AuthenticationManagerBuilder to configure.
     * @throws Exception if there is a problem during configuration.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("DEBUG: I am in the configureGlobal Security method");
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
