package com.example.mySpringApi.api.response;

import org.springframework.http.HttpStatus;

/**
 * Represents an HTTP error response.
 * This class is used to send an error message and status back to the client when an exception occurs.
 */
public class ErrorResponse {

    private final String message;
    private final HttpStatus httpStatus;

    /**
     * Constructs a new ErrorResponse with specified detail message and HTTP status code.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param httpStatus The HTTP status code that is suitable for this error (which is saved for later retrieval by the getHttpStatus() method).
     */
    public ErrorResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
