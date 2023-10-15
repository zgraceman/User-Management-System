package com.example.mySpringApi.model.dto;

/**
 * Data Transfer Object (DTO) representing the response structure for User-related operations.
 *
 * This class is primarily designed to shape the response returned to the client after
 * performing certain operations on the User entity. Since it's a record, it provides
 * a compact and immutable structure.
 *
 * The DTO omits certain sensitive or unnecessary details (like the raw password) that
 * shouldn't be exposed in responses.
 */
public record UserResponseDTO(
        int id,
        String name,
        String email,
        int age
) {}
