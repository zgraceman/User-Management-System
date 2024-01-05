package com.example.mySpringApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for password encoding.
 * <p>
 * This class defines a bean configuration for Spring Security's PasswordEncoder.
 * It uses BCryptPasswordEncoder, a strong hashing algorithm that incorporates a salt
 * to protect against rainbow table attacks.
 * <p>
 * The @Configuration annotation denotes that this class is a source of bean definitions.
 * The @Bean annotation on the passwordEncoder method signifies that the method returns a bean
 * to be managed by the Spring container. In this case, it's a PasswordEncoder bean.
 * <p>
 * The PasswordEncoder bean is used throughout the application wherever password hashing or comparison
 * is needed, ensuring a consistent and secure approach to handle user passwords.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Creates a PasswordEncoder bean using BCrypt hashing algorithm.
     *
     * @return PasswordEncoder instance to be used for encoding passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
