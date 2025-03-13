package com.twitterClone.twitterClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String username;
    private int likeCount;
    private int retweetCount;
    private int commentCount;
}
