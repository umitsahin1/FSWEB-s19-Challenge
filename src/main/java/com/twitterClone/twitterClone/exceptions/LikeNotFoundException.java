package com.twitterClone.twitterClone.exceptions;

import org.springframework.http.HttpStatus;

public class LikeNotFoundException extends TwitterException {

    public LikeNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
