package com.example.mySpringApi.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {

    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }
}
