package com.example.mySpringApi.api.advice;

import com.example.mySpringApi.api.response.ErrorResponse;
import com.example.mySpringApi.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles exceptions for the user-related controllers.
 * It intercepts exceptions thrown within controller methods and formats the error response.
 *
 * This class leverages Spring's @ControllerAdvice annotation to provide a centralized exception handling across all
 * controller classes. The goal is to have a consistent error response structure throughout the application.
 *
 * Specific types of exceptions can be handled by adding more methods annotated with @ExceptionHandler.
 */
@ControllerAdvice
public class UserExceptionHandler {

    /**
     * Handles UserNotFoundExceptions thrown in the application.
     * It builds an ErrorResponse object using the message and HTTP status from the exception
     * and returns it in the body of a ResponseEntity.
     *
     * @param userNotFoundException The UserNotFoundException that was thrown.
     * @return A ResponseEntity containing the ErrorResponse and the HTTP status.
     */
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(
                userNotFoundException.getMessage(),
                userNotFoundException.getHttpStatus()
        );

        return new ResponseEntity<>(errorResponse, userNotFoundException.getHttpStatus());
    }
}
