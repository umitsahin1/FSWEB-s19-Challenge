package com.twitterClone.twitterClone.controller;


import com.twitterClone.twitterClone.entity.Like;
import com.twitterClone.twitterClone.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class LikeController {


    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like/{tweetId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Like toggleLike(@PathVariable Long tweetId, @PathVariable Long userId) {
        return likeService.toggleLike(tweetId, userId);    }

}
