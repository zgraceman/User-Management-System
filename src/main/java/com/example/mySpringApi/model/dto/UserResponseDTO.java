package com.example.mySpringApi.model.dto;

public record UserResponseDTO(
        String name,
        String email,
        int age
) {}
