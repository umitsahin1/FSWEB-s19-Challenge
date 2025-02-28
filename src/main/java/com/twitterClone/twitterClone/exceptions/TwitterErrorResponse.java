package com.twitterClone.twitterClone.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TwitterErrorResponse {

    private String message;
    private int status;
    private Long timestamp;
}
