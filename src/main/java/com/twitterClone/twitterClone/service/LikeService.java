package com.twitterClone.twitterClone.service;


import com.twitterClone.twitterClone.entity.Like;

import java.util.List;
import java.util.Optional;

public interface LikeService {
    List<Like> getAll();
    Like getById(Long id);
    Like save(Like like);
    Like replaceOrCreate(Long id,Like like);
    void deleteById(Long id);
    Optional<Like> findByUserIdAndTweetId(Long userId, Long tweetId);
    boolean existsByUserIdAndTweetId(Long userId, Long tweetId);
    Like toggleLike(Long userId, Long tweetId);
}
