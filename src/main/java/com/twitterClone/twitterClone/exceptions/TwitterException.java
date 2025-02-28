package com.twitterClone.twitterClone.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class TwitterException extends RuntimeException {
    private HttpStatus status;

    public TwitterException(String message, HttpStatus status) {
        super(message);
        this.status =status;
    }
}
