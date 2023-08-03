package com.example.mySpringApi.exception;

import org.springframework.http.HttpStatus;

/**
 * Represents an exception specific to user-related operations.
 * This class is a part of the exception handling framework for the application.
 * It contains information about the error including a message,
 * the exception that caused this exception to be thrown, and the associated HTTP status code.
 *
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

    /**
     * This constructor creates a new UserException with a specific detail message, cause, and HTTP status code.
     * The cause argument is used to record the exception that led to this UserException being raised, and the
     * HTTP status code indicates the HTTP response status that should be returned to the client in case this
     * exception is thrown.
     *
     * @param message   The detail message for this exception, which is saved for later retrieval by the Throwable.getMessage() method.
     * @param cause     The root cause of this exception, which is saved for later retrieval by the Throwable.getCause() method.
     * @param httpStatus The HTTP status code associated with this exception, which is saved for later retrieval by the getHttpStatus() method.
     */
    public UserException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    /**
     * This getter method returns the HTTP status code associated with this UserException.
     * The HTTP status code provides additional information about the nature of the exception and
     * can be used to generate appropriate HTTP responses when the exception is caught and handled.
     *
     * @return The HTTP status code associated with this UserException.
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
