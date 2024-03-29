package com.example.mySpringApi.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when a requested user cannot be found.
 * It is a specific type of UserException, with a preset message and HTTP status code.
 *
 * This class is part of the exception handling framework for the application,
 * and is used to signal that a requested user-related resource does not exist.
 *
 * It extends UserException to leverage the application's global exception handling
 * and response formatting.
 */
public class
UserNotFoundException extends UserException {

    /**
     * Constructs a new UserNotFoundException with a preset detail message and HTTP status code.
     *
     * The detail message is "User not found", indicating that a requested user resource could not be located,
     * and the HTTP status code is HttpStatus.NOT_FOUND, indicating that the requested resource is not available.
     */
    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }

    /**
     * Constructs a new UserNotFoundException with a custom detail message and a preset HTTP status code.
     *
     * The HTTP status code is HttpStatus.NOT_FOUND, indicating that the requested resource is not available.
     * @param customMessage the detail message of the exception
     */
    public UserNotFoundException(String customMessage) {
        super(customMessage, HttpStatus.NOT_FOUND);
    }
}
