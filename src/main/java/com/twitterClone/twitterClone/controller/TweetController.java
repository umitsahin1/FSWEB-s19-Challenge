package com.twitterClone.twitterClone.controller;

import com.twitterClone.twitterClone.dto.TweetRequest;
import com.twitterClone.twitterClone.dto.TweetResponse;
import com.twitterClone.twitterClone.entity.Tweet;
import com.twitterClone.twitterClone.entity.User;
import com.twitterClone.twitterClone.exceptions.UserNotFoundException;
import com.twitterClone.twitterClone.repository.CommentRepository;
import com.twitterClone.twitterClone.repository.LikeRepository;
import com.twitterClone.twitterClone.repository.RetweetRepository;
import com.twitterClone.twitterClone.repository.UserRepository;
import com.twitterClone.twitterClone.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
public class TweetController  {

    @Autowired
    private TweetService tweetService;
    private final LikeRepository likeRepository;
    private final RetweetRepository retweetRepository;
    private final CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;



    @GetMapping
    public List<TweetResponse> getAllTweets() {
        List<Tweet> tweets = tweetService.getAll();
        return tweets.stream()
                .map(tweet -> {
                    int likeCount = likeRepository.getLikeCountByTweetId(tweet.getId());
                    int retweetCount = retweetRepository.getRetweetCountByTweetId(tweet.getId());
                    int commentCount = commentRepository.getCommentCountByTweetId(tweet.getId());
                    return new TweetResponse(
                            tweet.getId(),
                            tweet.getContent(),
                            tweet.getCreatedAt(),
                            tweet.getUser().getUsername(),
                            likeCount,
                            retweetCount,
                            commentCount
                    );
                })
                .toList();
    }
    @GetMapping("/findByUserId")
    public List<TweetResponse> getAll(@RequestParam Long userId) {
        List<Tweet> tweets = tweetService.findByUserId(userId);
        return tweets.stream()
                .map(tweet -> {
                    int likeCount = likeRepository.getLikeCountByTweetId(tweet.getId());
                    int retweetCount = retweetRepository.getRetweetCountByTweetId(tweet.getId());
                    int commentCount = commentRepository.getCommentCountByTweetId(tweet.getId());
                    return new TweetResponse(
                            tweet.getId(),
                            tweet.getContent(),
                            tweet.getCreatedAt(),
                            tweet.getUser().getUsername(),
                            likeCount,
                            retweetCount,
                            commentCount
                    );
                })
                .toList();
    }

    @GetMapping("/findById/{id}")
    public TweetResponse getById(@PathVariable Long id) {
        Tweet tweet = tweetService.getById(id);
        int likeCount = likeRepository.getLikeCountByTweetId(id);
        int retweetCount = retweetRepository.getRetweetCountByTweetId(id);
        int commentCount = commentRepository.getCommentCountByTweetId(id);

        return new TweetResponse(
                tweet.getId(),
                tweet.getContent(),
                tweet.getCreatedAt(),
                tweet.getUser().getUsername(),
                likeCount,
                retweetCount,
                commentCount
        );
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tweet save(@RequestBody TweetRequest tweetRequest,@RequestParam Long userId) {
        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequest.getContent());
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        tweet.setUser(user);
        return tweetService.save(tweet, userId);
    }

    @PutMapping("/{id}")
    public Tweet update(@PathVariable Long id, @RequestBody Tweet tweet) {
        Tweet existingTweet = tweetService.getById(id);
        existingTweet.setContent(tweet.getContent());
        return tweetService.save(existingTweet,id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tweetService.deleteById(id);
    }

}
