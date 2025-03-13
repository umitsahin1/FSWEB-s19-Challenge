package com.twitterClone.twitterClone.service;

import com.twitterClone.twitterClone.entity.Retweet;
import com.twitterClone.twitterClone.entity.Tweet;
import com.twitterClone.twitterClone.entity.User;
import com.twitterClone.twitterClone.exceptions.RetweetNotFoundException;
import com.twitterClone.twitterClone.exceptions.TweetNotFoundException;
import com.twitterClone.twitterClone.exceptions.UserNotFoundException;
import com.twitterClone.twitterClone.repository.RetweetRepository;
import com.twitterClone.twitterClone.repository.TweetRepository;
import com.twitterClone.twitterClone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RetweetServiceImpl implements RetweetService {

    private final RetweetRepository retweetRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Autowired
    public RetweetServiceImpl(RetweetRepository retweetRepository, UserRepository userRepository, TweetRepository tweetRepository) {
        this.retweetRepository = retweetRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public List<Retweet> getAll() {
        return retweetRepository.findAll();
    }

    @Override
    public Retweet getById(Long id) {
        return retweetRepository.findById(id).orElseThrow(() -> new RetweetNotFoundException("Retweet not found"));
    }

    @Override
    public Retweet save(Retweet retweet) {
        return retweetRepository.save(retweet);
    }

    @Override
    public Retweet replaceOrCreate(Long id, Retweet retweet) {
        Optional<Retweet> retweetOptional = retweetRepository.findById(id);
        if (retweetOptional.isPresent()) {
            retweet.setId(id);
            return retweetRepository.save(retweet);
        }
        return retweetRepository.save(retweet);
    }

    @Override
    public Retweet retweet(Long userId, Long tweetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFoundException("Tweet not found"));


        Tweet newTweet = new Tweet();
        newTweet.setUser(user);
        newTweet.setContent("Retweet: " + tweet.getContent());
        newTweet.setCreatedAt(LocalDateTime.now());
        tweetRepository.save(newTweet);

        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        return retweetRepository.save(retweet);
    }

    @Override
    public void removeRetweet(Long userId, Long tweetId) {
        Optional<Retweet> existingRetweet = retweetRepository.findByUserIdAndTweetId(userId, tweetId);

        if (existingRetweet.isPresent()) {
            Retweet retweet = existingRetweet.get();

            Tweet retweetedTweet = retweet.getTweet();
            tweetRepository.delete(retweetedTweet);

            retweetRepository.delete(retweet);
        }
    }

    @Override
    public boolean isRetweeted(Long userId, Long tweetId) {
        return retweetRepository.existsByUserIdAndTweetId(userId, tweetId);
    }


}
