package com.example.mySpringApi.model.dto;

import com.example.mySpringApi.model.Role;
import jakarta.validation.constraints.*;

import java.util.Set;

/**
 * Data Transfer Object (DTO) for User-related operations.
 *
 * This class is designed to transport user data, especially during interactions
 * between the client and the server. It comes equipped with validation
 * annotations to ensure the integrity of the data.
 *
 * As a record, it offers a concise way to declare immutable data-only classes.
 *
 */
public record UserDTO(

        int id,

        @NotNull(message = "Name cannot be null.")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
        String name,

        @Min(value = 0, message = "Age must be positive.")
        @Max(value = 150, message = "Age value is unrealistic.")
        int age,

        @NotNull(message = "Email cannot be null.")
        @Email(message = "Invalid email format.")
        String email,

        @NotNull(message = "Password cannot be null.")
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        @Pattern.List({
                @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain at least one digit."),
                @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain at least one lowercase character."),
                @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain at least one uppercase character."),
                @Pattern(regexp = "(?=.*[!@#$%^&*+=?-]).+", message = "Password must contain at least one special character.")
        })
        String rawPassword,

        Set<String> roles
) {}
