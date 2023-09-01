package com.example.mySpringApi.api.advice;

import com.example.mySpringApi.response.ResponseHandler;
import com.example.mySpringApi.exception.InvalidUserInputException;
import com.example.mySpringApi.exception.UserAlreadyExistsException;
import com.example.mySpringApi.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles exceptions for the user-related controllers.
 * It intercepts exceptions thrown within controller methods and formats the error response.
 *
 * This class leverages Spring's @ControllerAdvice annotation to provide a centralized exception handling across all
 * controller classes. The goal is to have a consistent error response structure throughout the application.
 *
 * This class now uses the ResponseHandler for generating error responses in a standard structure.
 * This enhances the consistency of all API responses, not just for the successful ones but also for the error scenarios.
 *
 * Specific types of exceptions can be handled by adding more methods annotated with @ExceptionHandler.
 */
@ControllerAdvice
@Slf4j
public class UserExceptionHandler {

    /**
     * Handles UserNotFoundExceptions thrown in the application.
     * It generates a standard API response using the ResponseHandler by passing the error message and HTTP status from the exception.
     *
     * @param userNotFoundException The UserNotFoundException that was thrown.
     * @return A ResponseEntity containing the standard API error response and the HTTP status.
     */
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {

        log.error("User not found. Stack Trace -->", userNotFoundException);

        return ResponseHandler.generateResponse(
                userNotFoundException.getMessage(),
                userNotFoundException.getHttpStatus(),
                null // pass null or any other relevant data in case of an exception
        );
    }

    /**
     * Handles UserAlreadyExistsException thrown in the application.
     *
     * @param ex The UserAlreadyExistsException that was thrown.
     * @return A ResponseEntity containing the standard API error response and the HTTP status.
     */
    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {

        log.error("User already exists. Stack Trace -->", ex);

        return ResponseHandler.generateResponse(
                ex.getMessage(),
                ex.getHttpStatus(),
                null // pass null or any other relevant data in case of an exception
        );
    }

    /**
     * Handles InvalidUserInputException thrown in the application.
     *
     * @param ex The InvalidUserInputException that was thrown.
     * @return A ResponseEntity containing the standard API error response and the HTTP status.
     */
    @ExceptionHandler(value = {InvalidUserInputException.class})
    public ResponseEntity<Object> handleInvalidUserInputException(InvalidUserInputException ex) {

        log.error("Invalid user input. Stack Trace -->", ex);

        return ResponseHandler.generateResponse(
                ex.getMessage(),
                ex.getHttpStatus(),
                null // pass null or any other relevant data in case of an exception
        );
    }

    /**
     * Handles validation exceptions triggered by {@code @Valid} annotations on DTO fields.
     *
     * This method captures errors from Spring's built-in validation, transforming them
     * into a standardized API error response using the ResponseHandler.
     *
     * @param ex The MethodArgumentNotValidException that was thrown.
     * @return A ResponseEntity containing the standard API error response and HTTP status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        log.error("Validation error. Stack Trace -->", ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseHandler.generateResponse(
                "Validation failed for the request.",
                HttpStatus.BAD_REQUEST,
                errors // this will send back the fields and their respective validation error messages
        );
    }
}
