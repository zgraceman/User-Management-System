package com.example.mySpringApi.model.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

public record UserDTO(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        int id,

        @NotNull(message = "Name cannot be null.")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
        String name,

        @NotNull(message = "Email cannot be null.")
        @Email(message = "Invalid email format.")
        String email,

        @Min(value = 0, message = "Age must be positive.")
        @Max(value = 150, message = "Age value is unrealistic.")
        int age,

        @NotNull(message = "Password cannot be null.")
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        @Pattern.List({
                @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain at least one digit."),
                @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain at least one lowercase character."),
                @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain at least one uppercase character."),
                @Pattern(regexp = "(?=.*[!@#$%^&*+=?-]).+", message = "Password must contain at least one special character.")
        })
        String rawPassword
) {}
