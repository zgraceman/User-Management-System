package com.example.mySpringApi.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when an attempt is made to create a user that already exists.
 *
 * The HTTP status is set to CONFLICT because the request could not be completed due to a conflict with the current state of the target resource.
 */
public class UserAlreadyExistsException extends UserException{

    /**
     * Creates a new instance of UserAlreadyExistsException.
     */
    public UserAlreadyExistsException() {
        super("User already exists", HttpStatus.CONFLICT);
    }
}
