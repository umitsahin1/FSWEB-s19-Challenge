package com.twitterClone.twitterClone.service;

import com.twitterClone.twitterClone.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> getAll();
    Comment getById(Long id);
    Comment save(Comment comment, Long tweetId);
    Comment replaceOrCreate(Long id,Comment comment);
    void deleteById(Long id);
    List<Comment> findByTweetId(Long tweetId);

}
