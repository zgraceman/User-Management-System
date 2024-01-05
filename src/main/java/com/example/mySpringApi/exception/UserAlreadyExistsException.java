package com.example.mySpringApi.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when an attempt is made to create a user that already exists.
 * <p>
 * The HTTP status is set to CONFLICT because the request could not be completed due to a conflict with the current state of the target resource.
 */
public class UserAlreadyExistsException extends UserException {

    /**
     * Constructor for creating a new UserAlreadyExistsException.
     *
     * @param message The detail message, saved for later retrieval by the Throwable.getMessage() method.
     */
    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause, HttpStatus.CONFLICT);
    }
}
