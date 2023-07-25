package com.example.mySpringApi.exception;

import org.springframework.http.HttpStatus;

/**
 * Represents an exception specific to user-related operations.
 * This class is a part of the exception handling framework for the application.
 * It contains information about the error including a message,
 * the exception that caused this exception to be thrown, and the associated HTTP status code.
 *
 * TODO: Integrate Lombok to reduce boilerplate code
 */
public class UserException extends RuntimeException {

    private final HttpStatus httpStatus;

    /**
     * Constructs a new UserException with specified detail message and HTTP status code.
     *
     * @param message The detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param httpStatus The HTTP status code that is suitable for this exception (which is saved for later retrieval by the getHttpStatus() method).
     */
    public UserException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
