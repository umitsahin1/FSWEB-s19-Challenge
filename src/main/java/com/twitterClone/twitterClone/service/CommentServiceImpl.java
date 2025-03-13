package com.twitterClone.twitterClone.service;

import com.twitterClone.twitterClone.entity.Comment;
import com.twitterClone.twitterClone.entity.Tweet;
import com.twitterClone.twitterClone.entity.User;
import com.twitterClone.twitterClone.exceptions.CommentNotFoundException;
import com.twitterClone.twitterClone.exceptions.TweetNotFoundException;
import com.twitterClone.twitterClone.repository.CommentRepository;
import com.twitterClone.twitterClone.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentRepository commentRepository;
    private final TweetRepository tweetRepository;

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    @Override
    public Comment save(Comment comment, Long tweetId) {

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFoundException("Tweet not found"));
        User user = tweet.getUser();

        comment.setTweet(tweet);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    @Override
    public Comment replaceOrCreate(Long id, Comment comment) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            comment.setId(id);
            return commentRepository.save(comment);
        }
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findByTweetId(Long tweetId) {
        return commentRepository.findByTweet_Id(tweetId);
    }


}
