package com.example.mySpringApi.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when invalid user input is encountered.
 *
 * The HTTP status is set to BAD_REQUEST because the server could not understand the request due to invalid syntax.
 */
public class InvalidUserInputException extends UserException {

    /**
     * Constructor for creating a new InvalidUserInputException.
     *
     * @param message The detail message, saved for later retrieval by the Throwable.getMessage() method.
     */
    public InvalidUserInputException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}