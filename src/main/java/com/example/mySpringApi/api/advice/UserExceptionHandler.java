package com.example.mySpringApi.api.advice;

import com.example.mySpringApi.api.response.ErrorResponse;
import com.example.mySpringApi.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                ex.getHttpStatus()
        );

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}
