package com.example.mySpringApi.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when invalid user input is encountered.
 *
 * The HTTP status is set to BAD_REQUEST because the server could not understand the request due to invalid syntax.
 */
public class InvalidUserInputException extends UserException {

    /**
     * Creates a new instance of InvalidUserInputException.
     */
    public InvalidUserInputException() {
        super("Invalid user input", HttpStatus.BAD_REQUEST);
    }
}