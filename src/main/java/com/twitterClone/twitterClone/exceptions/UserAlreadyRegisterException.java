package com.twitterClone.twitterClone.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyRegisterException extends TwitterException {

    public UserAlreadyRegisterException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
