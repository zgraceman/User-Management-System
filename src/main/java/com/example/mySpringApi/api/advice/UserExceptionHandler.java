package com.example.mySpringApi.api.advice;

import com.example.mySpringApi.exception.UserException;
import com.example.mySpringApi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// TODO 1. Integrate this exception handling into service layer
// TODO 2. Test for error: "500 Internal Server Error" again in postman
// TODO 3. Create and implement ErrorResponse class, instantiate and use in handleUserNotFoundException instead of UserException.
//     TODO 3a. Refactor UserException class, keeping HttpStatus field
//     TODO 3b. UserNotFoundException ... 
//     TODO 3c. UserExceptionHandler ...
//     TODO 3d. UserExceptionHandler ... @ExceptionHandler..
//     TODO 3e. Error Response w/ message & httpStatus
// TODO 4. Repeat TODO 2.
@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        UserException userException = new UserException(
                userNotFoundException.getMessage(),
                userNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(userException, HttpStatus.NOT_FOUND);
    }

}
