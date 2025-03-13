package com.twitterClone.twitterClone.controller;


import com.twitterClone.twitterClone.entity.Retweet;
import com.twitterClone.twitterClone.service.RetweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
public class RetweetController {

    private final RetweetService retweetService;

    @Autowired
    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping("/{userId}/{tweetId}")
    public String toggleRetweet(@PathVariable Long userId, @PathVariable Long tweetId) {
        boolean isRetweeted = retweetService.isRetweeted(userId, tweetId);

        if (isRetweeted) {
            retweetService.removeRetweet(userId, tweetId);
            return "Retweet removed";
        } else {
            retweetService.retweet(userId, tweetId);
            return "Tweet retweeted";
        }
    }
}
