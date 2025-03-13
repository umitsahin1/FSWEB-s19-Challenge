package com.twitterClone.twitterClone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TweetRequest {

    @NotBlank(message = "Content boş olamaz")
    @Size(max = 280, message = "Content 280 karakteri geçemez")
    private String content;
}
