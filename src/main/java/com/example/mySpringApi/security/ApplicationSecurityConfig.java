package com.example.mySpringApi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * This class is responsible for configuring Spring Security in the application.
 * It sets up authentication, authorization, and other security-related configurations.
 */
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    @Autowired
    private MyUserDetailsService userDetailsService;

    /**
     * Configures the SecurityFilterChain that specifies security constraints on the application.
     *
     * @param http the HttpSecurity object used to configure security settings.
     * @return the configured SecurityFilterChain.
     * @throws Exception thrown if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/","index","/css/*","/js/*").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic();
        return http.build();
    }

    /**
     * Creates a PasswordEncoder that uses the BCrypt hashing function.
     *
     * @return the configured BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the DaoAuthenticationProvider with a UserDetailsService and PasswordEncoder.
     *
     * @return the configured DaoAuthenticationProvider.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}