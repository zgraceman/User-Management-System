package com.example.mySpringApi.model.dto;

public record UserResponseDTO(
        int id,
        String name,
        String email,
        int age
) {}
