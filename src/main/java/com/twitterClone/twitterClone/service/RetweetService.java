package com.twitterClone.twitterClone.service;

import com.twitterClone.twitterClone.entity.Retweet;

import java.util.List;
import java.util.Optional;

public interface RetweetService {

    List<Retweet> getAll();
    Retweet getById(Long id);
    Retweet save(Retweet retweet);
    Retweet replaceOrCreate(Long id,Retweet retweet);
    Retweet retweet(Long userId, Long tweetId);
    void removeRetweet(Long userId,Long tweetId);
    boolean isRetweeted(Long userId, Long tweetId);

}
