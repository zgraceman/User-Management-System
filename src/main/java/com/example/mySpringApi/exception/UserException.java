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
public class UserException {

    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;

    /**
     * Constructs a new UserException with specified detail message, exception and HTTP status code.
     *
     * @param message The detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param throwable The exception that caused this exception to be thrown (which is saved for later retrieval by the Throwable.getCause() method).
     * @param httpStatus The HTTP status code that is suitable for this exception (which is saved for later retrieval by the getHttpStatus() method).
     */
    public UserException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
