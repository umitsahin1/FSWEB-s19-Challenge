package com.twitterClone.twitterClone.exceptions;

import org.springframework.http.HttpStatus;

public class TweetNotFoundException extends TwitterException {

    public TweetNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
