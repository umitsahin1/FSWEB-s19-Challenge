package com.twitterClone.twitterClone.service;

import com.twitterClone.twitterClone.entity.Tweet;

import java.util.List;

public interface TweetService {

    List<Tweet> getAll();
    Tweet getById(Long id);
    Tweet save(Tweet tweet, Long userId);
    Tweet replaceOrCreate(Long id,Tweet tweet);
    void deleteById(Long id);
    List<Tweet> findByUserId(Long userId);
}
